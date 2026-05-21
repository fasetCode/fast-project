package com.fastproject.netty;

import com.fastproject.service.ApplicationService;
import com.fastproject.utils.utils.JsonUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Netty WebSocket 服务端启动类
 * 由 Spring 容器管理，负责 Netty 服务生命周期
 */
@Component
@Slf4j
public class NettyWebSocketServer {

    // WebSocket 服务监听端口 (从配置文件读取，默认 8888)
    @Value("${netty.websocket.port:8888}")
    private int port;

    // WebSocket 服务监听路径 (从配置文件读取，默认 /ws)
    @Value("${netty.websocket.path:/ws}")
    private String websocketPath;

    // 主线程组，负责接收客户端连接
    private EventLoopGroup bossGroup;
    // 工作线程组，负责处理已建立连接的业务逻辑
    private EventLoopGroup workerGroup;

    private final ApplicationService applicationService;


    // Netty 服务线程，避免阻塞 Spring 主线程
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public NettyWebSocketServer(ApplicationService applicationService ) {
        this.applicationService = applicationService;
    }

    /**
     * Spring 容器启动后执行，启动 Netty 服务
     */
    @PostConstruct
    public void start() {
        executorService.execute(() -> {
            log.info("正在启动 Netty WebSocket 服务，监听端口: {}", port);
            
            // 初始化 Netty 服务配置
            bossGroup = new NioEventLoopGroup(1); // 接收连接线程池
            workerGroup = new NioEventLoopGroup(); // 业务处理线程池

            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class) // 指定 NIO 传输模式
                        .childHandler(new NettyWebSocketServerInitializer(websocketPath,applicationService )); // 设置管道初始化器

                // 绑定端口并同步阻塞等待启动成功
                ChannelFuture f = b.bind(port).sync();
                log.info("Netty WebSocket 服务启动成功，访问路径: ws://localhost:{}{}", port, websocketPath);
                
                // 等待服务端监听通道关闭
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("Netty 服务运行期间发生中断异常", e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.error("Netty 服务启动失败", e);
            } finally {
                // 优雅关闭 Netty 资源
                stop();
            }
        });
    }

    /**
     * Spring 容器销毁前执行，优雅关闭 Netty 资源
     */
    @PreDestroy
    public void stop() {
        log.info("正在关闭 Netty WebSocket 服务...");
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        executorService.shutdown();
        log.info("Netty WebSocket 服务已安全关闭");
    }
}
