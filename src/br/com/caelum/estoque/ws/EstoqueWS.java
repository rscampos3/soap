package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ItemValidador;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

@WebService
public class EstoqueWS {
	private ItemDao dao = new ItemDao();

	@WebMethod(operationName = "BuscarTodosOsItens")
	@ResponseWrapper(localName = "itens")
	@WebResult(name = "itens")
	@RequestWrapper(localName = "listaItens")
	public List<Item> getItens(@WebParam(name = "filtro") Filtros filtros) {
		System.out.println("Chamando getItens()");
		List<Filtro> lista = filtros.getLista();
		List<Item> itensResultado = dao.todosItens(lista);
		return itensResultado;
	}

	@WebMethod(operationName = "CadastrarItem")
	@WebResult(name = "item")
	public Item cadastrarItem(@WebParam(name = "token", header = true) TokenUsuario token,
			@WebParam(name = "item") Item item) throws AutorizacaoException {

		System.out.println("Cadastrando item " + item + ", Token: " + token);

		if (!new TokenDao().ehValido(token)) {
			throw new AutorizacaoException("Autorização falhou");
		}

		new ItemValidador(item).validate();
		
		this.dao.cadastrar(item);

		return item;
	}
}
