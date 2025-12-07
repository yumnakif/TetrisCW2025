package com.comp2042.logic.board;

import com.comp2042.ui.ViewData;

/**
 * Data transfer object that combines row clearance results  with view data
 * Used to return results of down movement and the updated visual representation of game state
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Constructs a DownData object with specified clearance results and view data
     * @param clearRow the row clearance results
     * @param viewData the updated view data for rendering
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Get the row clearing results
     * @return ClearRow object containing the clearance information
     */
    public ClearRow getClearRow() {
        return clearRow;
    }


    /**
     * Gets the updated view data
     * @return ViewData object containing the current visual data of the game
     */
    public ViewData getViewData() {
        return viewData;
    }
}
