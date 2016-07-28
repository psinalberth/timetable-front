package br.edu.ifma.csp.timetable.repository;

import java.util.List;

import br.edu.ifma.csp.timetable.model.Entidade;

/**
 * Repositório de operações genéricas da aplicação.
 * 
 * @author inalberth
 *
 * @param <T> Modelo a ser abstraído
 */
public interface Repository<T extends Entidade> {
	
	/**
	 * Persiste um objeto no repositório de dados.
	 * 
	 * @param type Objeto a ser persistido.
	 */
	public void save(T type);
	
	/**
	 * Recupera um objeto a partir do identificador primário.
	 * 
	 * @param id Identificador primário do objeto.
	 * @return Retorna o objeto recuperado ou <code>null</code> caso não seja encontrado.
	 */
	public T byId(int id);
	
	
	/**
	 * Recupera todos os objetos do modelo referenciado.
	 * 
	 * @return Retorna uma lista de objetos ou <code>null</code> caso não sejam encontrados.
	 */
	public List<T> all();
	
	/**
	 * Recupera todos os objetos do modelo referenciado a partir de um critério de seleção.
	 * 
	 * @return Retorna uma lista de objetos ou <code>null</code> caso não sejam encontrados.
	 */
	public List<T> allBy();
	
	/**
	 * Atualiza um objeto persistido no repositório de dados.
	 * 
	 * @param type Objeto a ser atualizado.
	 */
	public void update(T type);
	
	/**
	 * Remove um objeto persistido no repositório de dados.
	 * 
	 * @param type Objeto a ser removido.
	 */
	public void delete(T type);
}