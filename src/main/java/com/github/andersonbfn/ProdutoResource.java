package com.github.andersonbfn;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {
  
  @GET
  public List<Produto> buscarTodosProdutos() {
    return Produto.listAll();
  }
  
  @POST
  @Transactional
  public void registrarProduto(final CadastrarProdutoDTO dto) {
    final Produto p = new Produto();
    p.setNome(dto.getNome());
    p.setValor(dto.getValor());
    p.persist();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public void atualizarProduto(@PathParam("id") final Long id, final CadastrarProdutoDTO dto) {
    final Optional<Produto> optProd = Produto.findByIdOptional(id);
    
    if (optProd.isPresent()) {
      optProd.get().setNome(dto.getNome());
      optProd.get().setValor(dto.getValor());
      optProd.get().persist();
    } else {
      throw new NotFoundException();
    }
  }
  
  @DELETE
  @Path("{id}")
  @Transactional
  public void apagarProduto(@PathParam("id") final Long id) {
    Produto.<Produto>findByIdOptional(id).ifPresentOrElse(Produto::delete, () -> {throw new NotFoundException();});
  }

}
