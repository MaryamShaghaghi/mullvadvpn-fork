use fast_socks5::server::{AcceptAuthentication, Socks5Server};
use futures::StreamExt;
use std::{io, net::SocketAddr};

#[derive(thiserror::Error, Debug)]
pub enum Error {
    #[error("Failed to start SOCKS5 server")]
    StartSocksServer(#[source] io::Error),
}

pub struct Handle {
    handle: tokio::task::JoinHandle<()>,
}

/// Spawn a SOCKS server bound to `bind_addr`
pub async fn spawn(bind_addr: SocketAddr) -> Result<Handle, Error> {
    let socks_server: Socks5Server<AcceptAuthentication> = Socks5Server::bind(bind_addr)
        .await
        .map_err(Error::StartSocksServer)?;

    let handle = tokio::spawn(async move {
        let mut incoming = socks_server.incoming();

        while let Some(new_client) = incoming.next().await {
            match new_client {
                Ok(socket) => {
                    let fut = socket.upgrade_to_socks5();

                    // Act as normal SOCKS server
                    tokio::spawn(async move {
                        match fut.await {
                            Ok(_socket) => log::info!("socks client disconnected"),
                            Err(error) => log::error!("socks client failed: {error}"),
                        }
                    });
                }
                Err(error) => {
                    log::error!("failed to accept socks client: {error}");
                }
            }
        }
    });
    Ok(Handle { handle })
}

impl Handle {
    pub fn close(&self) {
        self.handle.abort();
    }
}

impl Drop for Handle {
    fn drop(&mut self) {
        self.close();
    }
}
