package com.koreait.pjt.vo;

public class BoardDomain extends BoardVO{
	private String nm;
	private int yn_like;
	private int record_cnt;//페이지당 나오는 레코드 수(글 수)
	private int eIdx;
	private int sIdx;
	private String searchText;
	private String profile_img;
	private int like_count;
	private int cnt_count;
	
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getCnt_count() {
		return cnt_count;
	}
	public void setCnt_count(int cnt_count) {
		this.cnt_count = cnt_count;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public int geteIdx() {
		return eIdx;
	}
	public void seteIdx(int eIdx) {
		this.eIdx = eIdx;
	}
	public int getsIdx() {
		return sIdx;
	}
	public void setsIdx(int sIdx) {
		this.sIdx = sIdx;
	}
	public int getRecord_cnt() {
		return record_cnt;
	}
	public void setRecord_cnt(int record_cnt) {
		this.record_cnt = record_cnt;
	}
	public int getYn_like() {
		return yn_like;
	}
	public void setYn_like(int yn_like) {
		this.yn_like = yn_like;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}

}
