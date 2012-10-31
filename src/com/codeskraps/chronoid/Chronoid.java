/**
 * Chronoid
 * Copyright (C) Carles Sentis 2012 <codeskraps@gmail.com>
 *
 * Chronoid is free software: you can
 * redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later
 * version.
 *  
 * Chronoid is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *  
 * You should have received a copy of the GNU
 * General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.codeskraps.chronoid;

public class Chronoid {
//	private final short STARTED = 0;
//	private final short PAUSED = 1;
	private final short STOPPED = 2;
	
	private short gameStarted;
	private boolean firstPlayer;
	private short minPlayerOne;
	private short minPlayerTwo;
	private String gameType = null;
	private short timeDelay;
	
	private long baseTime;
	private long playerOneBaseTime;
	private long playerTwoBaseTime;
	private long pauseTime;
	
	private ChronoidApplication chronoidApplication;
	
	public Chronoid (ChronoidApplication gameClockApplication){
		setGameStarted(STOPPED);
		setFirstPlayer(false);
		setPauseTime(0);
		setChronoidApplications(gameClockApplication);
	}

	public short getGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(short gameStarted) {
		this.gameStarted = gameStarted;
	}

	public boolean getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(boolean firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Short getMinPlayerOne() {
		return minPlayerOne;
	}

	public void setMinPlayerOne(Short minPlayerOne) {
		this.minPlayerOne = minPlayerOne;
	}

	public Short getMinPlayerTwo() {
		return minPlayerTwo;
	}

	public void setMinPlayerTwo(Short minPlayerTwo) {
		this.minPlayerTwo = minPlayerTwo;
	}

	public ChronoidApplication getChronoidApplications() {
		return chronoidApplication;
	}

	public void setChronoidApplications(ChronoidApplication chronoidApplications) {
		this.chronoidApplication = chronoidApplications;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public short getTimeDelay() {
		return timeDelay;
	}

	public void setTimeDelay(short timeDelay) {
		this.timeDelay = timeDelay;
	}

	public long getBaseTime() {
		return baseTime;
	}

	public void setBaseTime(long baseTime) {
		this.baseTime = baseTime;
	}

	public long getPlayerOneBaseTime() {
		return playerOneBaseTime;
	}

	public void setPlayerOneBaseTime(long playerOneBaseTime) {
		this.playerOneBaseTime = playerOneBaseTime;
	}

	public long getPlayerTwoBaseTime() {
		return playerTwoBaseTime;
	}

	public void setPlayerTwoBaseTime(long playerTwoBaseTime) {
		this.playerTwoBaseTime = playerTwoBaseTime;
	}

	public long getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}
}
