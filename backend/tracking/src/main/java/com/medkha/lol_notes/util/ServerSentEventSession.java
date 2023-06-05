package com.medkha.lol_notes.util;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class ServerSentEventSession{
    public SseEmitter sseEmitter;
    public int lastCheckedIndex = -1;

    public ServerSentEventSession(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }
}
