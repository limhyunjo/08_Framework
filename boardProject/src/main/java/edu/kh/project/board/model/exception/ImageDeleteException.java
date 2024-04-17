package edu.kh.project.board.model.exception;

// 사용자 정의 예외는 하나 만들어서 복붙하는 경우가 많음
/*
 * 이미지 삭제 중 문제 발생 시 사용할 사용자 정의 예외
 * */
public class ImageDeleteException extends RuntimeException{

	 public ImageDeleteException() {
		 super("이미지 삭제 중 예외 발생");
	 }
	 
		public ImageDeleteException(String message) {
			super(message);
		}

}
