package br.edu.ifma.csp.timetable.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.edu.ifma.csp.timetable.model.Perfil;
import br.edu.ifma.csp.timetable.model.Transacao;
import br.edu.ifma.csp.timetable.repository.Transacoes;

@Stateless
public class TransacaoDao extends RepositoryDao<Transacao> implements Transacoes {

	@SuppressWarnings("unchecked")
	@Override
	public List<Transacao> byPerfil(Perfil perfil) {
		
		String sql = 
		
		"select tr.* from TRANSACAO tr " +
			"inner join PERMISSAO per on " +
				"per.ID_TRANSACAO = tr.ID_TRANSACAO and " +
				"per.ID_PERFIL = :perfilId";
		
		return manager.createNativeQuery(sql, Transacao.class).setParameter("perfilId", perfil.getId()).getResultList();
	}

}
