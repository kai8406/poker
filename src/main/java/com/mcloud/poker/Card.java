package com.mcloud.poker;

import lombok.Data;

/**
 * 扑克
 * 
 * @author Sobey
 *
 */
@Data
public class Card implements Comparable<Card> {

	/**
	 * 扑克花色
	 */
	public Color color;

	/**
	 * 点数. A为14.
	 */
	public int number;

	@Override
	public int compareTo(Card o) {
		int i = this.getNumber() - o.getNumber();
		return i;
	}

}
