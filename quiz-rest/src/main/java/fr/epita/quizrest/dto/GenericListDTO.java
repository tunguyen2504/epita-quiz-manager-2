package fr.epita.quizrest.dto;

import java.util.List;

/**
 * @author Anh Tu
 *
 * @param <T>
 */
public class GenericListDTO<T> {

	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
