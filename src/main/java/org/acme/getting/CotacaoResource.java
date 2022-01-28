package org.acme.getting;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.model.Cotacao;
import org.acme.services.CotacaoService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/cotacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CotacaoResource {

  @Inject
  CotacaoService cotacaoService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  // @Operation(summary = "Teste", description = "description")
  // @APIResponse(responseCode = "200", description = "Ok", content =
  // @Content(mediaType = "text/plain"))
  public String ok() {
    return "its ok!";
  }

  @GET
  @Path("{data}")
  @Produces(MediaType.APPLICATION_JSON)
  // @Operation(summary = "Fetch Cotação", description = "Retorna uma cotação dado
  // uma data específica")
  @Operation(summary = "Retorna a cotação do dia selecionado - MM-DD-YYYY")
  @APIResponse(responseCode = "200", //
      content = @Content(//
          mediaType = MediaType.APPLICATION_JSON, //
          schema = @Schema(//
              implementation = Cotacao.class, //
              type = SchemaType.STRING)))
  public Cotacao fetchCotacao(@PathParam String data) {

    try {

      Cotacao c = cotacaoService.fetchCotacao(data);
      return c;

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;

  }

}