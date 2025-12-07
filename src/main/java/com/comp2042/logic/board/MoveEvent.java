package com.comp2042.logic.board;

import com.comp2042.input.EventSource;
import com.comp2042.input.EventType;

/**
 * Represents a game action with source and event type
 * Used to .distinguish between system or user initiated events
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Constructs the move event with the event source and type
     * @param eventType event type from enumeration
     * @param eventSource event source from enumeration
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Type of movement event
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Source of movement event
     * @return the event source
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
