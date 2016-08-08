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
	 * Recupera um objeto a partir de uma coluna selecionada.
	 * 
	 * @param coluna Coluna a ser utilizada como critério de busca.
	 * @param valor Valor da coluna a ser procurado.
	 * @return Retorna o objeto recuperado ou <code>null</code> caso não seja encontrado.
	 */
	public T by(String coluna, Object valor);
	
	/**
	 * Recupera todos os objetos do modelo referenciado.
	 * 
	 * @return Retorna uma lista de objetos ou <code>null</code> caso não sejam encontrados.
	 */
	public List<T> all();
	
	/**
	 * Recupera todos os objetos do modelo referenciado a partir de um critério de seleção.
	 * 
	 * @param coluna Coluna a ser utilizada como critério de busca.
	 * @param valor Valor da coluna a ser procurado.
	 * @return Retorna uma lista de objetos ou <code>null</code> caso não sejam encontrados.
	 */
	public List<T> allBy(String coluna, Object valor);
	
	/**
	 * Remove um objeto persistido no repositório de dados.
	 * 
	 * @param type Objeto a ser removido.
	 */
	public void delete(T type);
}
