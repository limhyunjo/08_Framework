package edu.kh.todo.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.todo.model.dto.Todo;

public interface TodoService {

	/** 할 일 목록 + 완료된 할 일 개수 조회
	 * @return map
	 */
	Map<String, Object> selectAll();

	int addTodo(String todoTitle, String todoContent);

	/** 할 일 상세 조회
	 * @param todoNo
	 * @return todo
	 */
	Todo todoDetail(int todoNo);

	/** 할 일 제거
	 * @param todoNo
	 * @return map
	 */
	int todoDelete(int todoNo);

	/** 할 일 수정
	 * @param todo
	 * @return result
	 */
	int todoUpdate(Todo todo);

	/** 완료 여부 수정
	 * @param todo
	 * @return result
	 */
	int changeComplete(Todo todo);

	/** 전체 할 일 개수 조회
	 * @return totalCount
	 */
	int getTotalCount();

	/** 완료된 할 일 개수 조회
	 * @return completeCount
	 */
	int getCompleteCount();

	/**할 일 목록 조회
	 * @return todoList
	 */
	List<Todo> selectList();

}
