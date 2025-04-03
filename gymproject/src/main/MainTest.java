package main;

import gym.dao.AdminDAO;
import registerview.RegisterMember;

public class MainTest {

	public static void main(String[] args) {
		// 회원가입
		RegisterMember register = new RegisterMember();
		register.registerMember();

		// 관리자 정보 수정
		AdminDAO adminupdate = new AdminDAO();
		adminupdate.upDateAdmin();

	}

}
