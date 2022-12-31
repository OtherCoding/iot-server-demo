package com.example.iot.server.common.util;

import java.util.concurrent.*;

/**
 * @author xupeng
 * @create 2022/12/23 17:40
 * @description
 */
public class ThreadPoolUtil {
    /**
     * 默认线程池线程个数
     */
    public static final int AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors() * 2;



    /**
     * 默认线程池
     */
    public static final ExecutorService DEFAULT_PROCESS_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSOR,
            AVAILABLE_PROCESSOR,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("default-process_executor", true), new ThreadPoolExecutor.AbortPolicy());
}
