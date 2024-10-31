package br.com.springboot.treinamento_dev.controllers;

import br.com.springboot.treinamento_dev.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.springboot.treinamento_dev.model.Suporte;
import br.com.springboot.treinamento_dev.model.Auth;
import br.com.springboot.treinamento_dev.model.Funcoes;
import br.com.springboot.treinamento_dev.model.Pessoas;
import br.com.springboot.treinamento_dev.model.Suporte2;
import br.com.springboot.treinamento_dev.model.Tipos;

import br.com.springboot.treinamento_dev.repository.UserRepository;
import br.com.springboot.treinamento_dev.repository.DataRepository;
import br.com.springboot.treinamento_dev.repository.GlpiRepository;
import br.com.springboot.treinamento_dev.repository.TipoRepository;
import br.com.springboot.treinamento_dev.repository.PeriodoRepository;
import br.com.springboot.treinamento_dev.repository.DescricaoRepository;

import br.com.springboot.treinamento_dev.repository.funcoes.AllFuncoesRepository;
import br.com.springboot.treinamento_dev.repository.pessoas.AllPessoasRepository;
import br.com.springboot.treinamento_dev.repository.suporte2.AllSuporteRepository;
import br.com.springboot.treinamento_dev.repository.tipos.AllTiposRepository;

/*
 *
 * A sample greetings controller to return greeting text
 */
@CrossOrigin("*")
@RestController
public class GreetingsController {
	
	@Autowired /*IC /CD ou CDI - injeção de dependência*/
	private UserRepository userRepository;
	
	@Autowired
	private DataRepository dataRepository;
	
	@Autowired
	private GlpiRepository glpiRepository;
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Autowired
	private DescricaoRepository descricaoRepository;
	
	@Autowired
	private AllFuncoesRepository allFuncoesRepository;
	
	@Autowired
	private AllPessoasRepository allPessoasRepository;
	
	@Autowired
	private AllSuporteRepository allSuporteRepository;
	
	@Autowired
	private AllTiposRepository allTiposRepository;
	
	
	/***********REACT***********************************************************************************/
	
	@PostMapping(value = "auth")
    @ResponseBody
    public Map<String, String> auth(@RequestBody Auth authToken) { 
    
		 Map<String, String> response = new HashMap<>();
	        response.put("accessToken", "aaaaa.bbbb");
	        
    	return response;
    	}
	
	////////////////////////////FUNCOES////////////////////////////////////////
	
	  @GetMapping("/funcoes")
	  public ResponseEntity<List<Funcoes>> getAllFuncoes(
	        @RequestParam(required = false, name="nome_like") String title,
	        @RequestParam(defaultValue = "1", name="_page") int page,
	        @RequestParam(defaultValue = "3", name="_limit") int size
	      ) {

	    try {
	      List<Funcoes> funcoes = new ArrayList<Funcoes>();
	      Pageable paging = PageRequest.of((page-1), size);
	      
	      Page<Funcoes> pageTuts;
	      if (title == null) {
	        pageTuts = allFuncoesRepository.findAll(paging);
	      }else {
	    	    List<Funcoes> allCustomers = allFuncoesRepository.findByNameContaining(title);
			    int start = (int) paging.getOffset();
			    int end = Math.min((start + paging.getPageSize()), allCustomers.size());

			    List<Funcoes> pageContent = allCustomers.subList(start, end);

	        pageTuts = new PageImpl<>(pageContent, paging, allCustomers.size());;
	      }
	      funcoes = pageTuts.getContent();

	      List<Funcoes> response = funcoes;

	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	 
	    @RequestMapping(value = "/funcoes/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public  ResponseEntity<Funcoes> funcoesById(@PathVariable String id) {
	    
	    try {
			Funcoes funcoes = allFuncoesRepository.findById(Long.parseLong(id)).get();
					    	
	    	return new ResponseEntity<Funcoes>(funcoes, HttpStatus.OK);
	    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	    }
	    
	    @PostMapping(value = "funcoes") //mapeia a url
	    @ResponseBody //descrição da resposta
	    public ResponseEntity<Funcoes> salvar(@RequestBody Funcoes funcoes) { //Reebe os dados para salvar
	    try {
	    	Funcoes chamado = allFuncoesRepository.save(funcoes);
	    	
	    	return new ResponseEntity<Funcoes>(chamado, HttpStatus.CREATED);
	    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	    }
	    
	    @RequestMapping(value = "/funcoes/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public  ResponseEntity<?> funcoesUpdateById(@PathVariable String id, @RequestBody Funcoes funcoes) {
	    
	    try {
	    	
	    	if(funcoes.getId() == null) {
	    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
	    	}
	    	
			Funcoes chamado = allFuncoesRepository.saveAndFlush(funcoes);
					    	
	    	return new ResponseEntity<Funcoes>(chamado, HttpStatus.OK);
	    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	    }
	    
	    @RequestMapping(value = "/funcoes/{id}", method = RequestMethod.DELETE)
	    @ResponseBody
	    public  ResponseEntity<String> funcoesDeleteById(@PathVariable String id) {
	    
	    try {
			allFuncoesRepository.deleteById(Long.parseLong(id));
					    	
	    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
	    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    
	    }
	    
	 /////////////////////////////SUPORTE2//////////////////////////////////////////////////////////
	    
	    @GetMapping("/suporte")
		  public ResponseEntity<List<Suporte2>> getAllSuporte(
		        @RequestParam(required = false, name="descricao_like") String title,
		        @RequestParam(required = false, name="data_like") String date,
		        @RequestParam(defaultValue = "1", name="_page") int page,
		        @RequestParam(defaultValue = "3", name="_limit") int size
		      ) {

		    try {
		      List<Suporte2> suporte = new ArrayList<Suporte2>();
		      Pageable paging = PageRequest.of((page-1), size);
		      
		      Page<Suporte2> pageTuts;
		      
		      if (title == null && date == null) {
		        pageTuts = allSuporteRepository.findAll(paging);
		      }
		      else if(title != null && date == null) {
		    	  
		    	    List<Suporte2> allCustomers = allSuporteRepository.findByDescricaoContaining(title);
				    int start = (int) paging.getOffset();
				    int end = Math.min((start + paging.getPageSize()), allCustomers.size());

				    List<Suporte2> pageContent = allCustomers.subList(start, end);
				    

		        pageTuts = new PageImpl<>(pageContent, paging, allCustomers.size());
		      } 
		      else if(title == null && date != null) {
		    	  
		    	List<Suporte2> allCustomers = allSuporteRepository.findByDateContaining(date);
			    int start = (int) paging.getOffset();
			    int end = Math.min((start + paging.getPageSize()), allCustomers.size());

			    List<Suporte2> pageContent = allCustomers.subList(start, end);
			    

	            pageTuts = new PageImpl<>(pageContent, paging, allCustomers.size());
   
		      } else {
		    	  	List<Suporte2> allCustomers = allSuporteRepository.findByDateAndDescricaoContaining(date, title);
				    int start = (int) paging.getOffset();
				    int end = Math.min((start + paging.getPageSize()), allCustomers.size());

				    List<Suporte2> pageContent = allCustomers.subList(start, end);
				    

		            pageTuts = new PageImpl<>(pageContent, paging, allCustomers.size());

		      }
		      
		      suporte = pageTuts.getContent();

		      List<Suporte2> response = suporte;
		      
		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		  }
		 
		    @RequestMapping(value = "/suporte/{id}", method = RequestMethod.GET)
		    @ResponseBody
		    public  ResponseEntity<Suporte2> suporteById(@PathVariable String id) {
		    
		    try {
				Suporte2 suporte = allSuporteRepository.findById(Long.parseLong(id)).get();
						    	
		    	return new ResponseEntity<Suporte2>(suporte, HttpStatus.OK);
		    } catch (Exception e) {
			      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		    
		    }
		    
		    @PostMapping(value = "suporte") //mapeia a url
		    @ResponseBody //descrição da resposta
		    public ResponseEntity<Suporte2> salvarSuporte(@RequestBody Suporte2 suporte) { //Reebe os dados para salvar
		    try {
		    	Suporte2 chamado = allSuporteRepository.save(suporte);
		    	
		    	return new ResponseEntity<Suporte2>(chamado, HttpStatus.CREATED);
		    } catch (Exception e) {
			      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		    
		    }
		    
		    @RequestMapping(value = "/suporte/{id}", method = RequestMethod.PUT)
		    @ResponseBody
		    public  ResponseEntity<?> suporteUpdateById(@PathVariable String id, @RequestBody Suporte2 suporte) {
		    
		    try {
		    	
		    	if(suporte.getId() == null) {
		    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
		    	}
		    	
				Suporte2 chamado = allSuporteRepository.saveAndFlush(suporte);
						    	
		    	return new ResponseEntity<Suporte2>(chamado, HttpStatus.OK);
		    } catch (Exception e) {
			      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		    
		    }
		    
		    @RequestMapping(value = "/suporte/{id}", method = RequestMethod.DELETE)
		    @ResponseBody
		    public  ResponseEntity<String> suporteDeleteById(@PathVariable String id) {
		    
		    try {
				allSuporteRepository.deleteById(Long.parseLong(id));
						    	
		    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
		    } catch (Exception e) {
			      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		    
		    }

	///////////////////////////////////////PESSOAS////////////////////////////////////////////////////////////
		    
		    @GetMapping("/pessoas")
			  public ResponseEntity<List<Pessoas>> getAllPessoas(
			        @RequestParam(required = false, name="nomeCompleto_like") String title,
			        @RequestParam(defaultValue = "1", name="_page") int page,
			        @RequestParam(defaultValue = "3", name="_limit") int size
			      ) {

			    try {
			      List<Pessoas> pessoas = new ArrayList<Pessoas>();
			      Pageable paging = PageRequest.of((page-1), size);
			      
			      Page<Pessoas> pageTuts;
			      if (title == null)
			        pageTuts = allPessoasRepository.findAll(paging);
			      else {
  			    	    
			    	    List<Pessoas> allCustomers = allPessoasRepository.findByNameContaining(title);
					    int start = (int) paging.getOffset();
					    int end = Math.min((start + paging.getPageSize()), allCustomers.size());

					    List<Pessoas> pageContent = allCustomers.subList(start, end);

			        pageTuts = new PageImpl<>(pageContent, paging, allCustomers.size());
			      }
			      pessoas = pageTuts.getContent();

			      List<Pessoas> response = pessoas;
			      
			      return new ResponseEntity<>(response, HttpStatus.OK);
			    } catch (Exception e) {
			      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			    }
			  }
			 
			    @RequestMapping(value = "/pessoas/{id}", method = RequestMethod.GET)
			    @ResponseBody
			    public  ResponseEntity<Pessoas> pessoasById(@PathVariable String id) {
			    
			    try {
					Pessoas pessoas = allPessoasRepository.findById(Long.parseLong(id)).get();
							    	
			    	return new ResponseEntity<Pessoas>(pessoas, HttpStatus.OK);
			    } catch (Exception e) {
				      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			    
			    }
			    
			    @PostMapping(value = "pessoas") //mapeia a url
			    @ResponseBody //descrição da resposta
			    public ResponseEntity<Pessoas> salvarPessoa(@RequestBody Pessoas pessoa) { //Reebe os dados para salvar
			    try {
			    	Pessoas chamado = allPessoasRepository.save(pessoa);
			    	
			    	return new ResponseEntity<Pessoas>(chamado, HttpStatus.CREATED);
			    } catch (Exception e) {
				      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			    
			    }
			    
			    @RequestMapping(value = "/pessoas/{id}", method = RequestMethod.PUT)
			    @ResponseBody
			    public  ResponseEntity<?> pessoasUpdateById(@PathVariable String id, @RequestBody Pessoas pessoas) {
			    
			    try {
			    	
			    	if(pessoas.getId() == null) {
			    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
			    	}
			    	
					Pessoas chamado = allPessoasRepository.saveAndFlush(pessoas);
							    	
			    	return new ResponseEntity<Pessoas>(chamado, HttpStatus.OK);
			    } catch (Exception e) {
				      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			    
			    }
			    
			    @RequestMapping(value = "/pessoas/{id}", method = RequestMethod.DELETE)
			    @ResponseBody
			    public  ResponseEntity<String> pessoasDeleteById(@PathVariable String id) {
			    
			    try {
					allPessoasRepository.deleteById(Long.parseLong(id));
							    	
			    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
			    } catch (Exception e) {
				      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			    
			    }
			    
    //////////////////////////////////////////TIPO////////////////////////////////////////////////////////
			    
			    @GetMapping("/tipos")
				  public ResponseEntity<List<Tipos>> getAllTipos(
				        @RequestParam(required = false, name="nome_like") String title,
				        @RequestParam(defaultValue = "1", name="_page") int page,
				        @RequestParam(defaultValue = "3", name="_limit") int size
				      ) {

				    try {
				      List<Tipos> tipos = new ArrayList<Tipos>();
				      Pageable paging = PageRequest.of((page-1), size);
				      
				      Page<Tipos> pageTuts;
				      if (title == null)
				        pageTuts = allTiposRepository.findAll(paging);
				      else {
				    	  
				    	    List<Tipos> allCustomers = allTiposRepository.findByNameContaining(title);
						    int start = (int) paging.getOffset();
						    int end = Math.min((start + paging.getPageSize()), allCustomers.size());

						    List<Tipos> pageContent = allCustomers.subList(start, end);

				            pageTuts = new PageImpl<>(pageContent, paging, allCustomers.size());
				      }
				      tipos = pageTuts.getContent();

				      List<Tipos> response = tipos;
				      
				      return new ResponseEntity<>(response, HttpStatus.OK);
				    } catch (Exception e) {
				      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				    }
				  }
				 
				    @RequestMapping(value = "/tipos/{id}", method = RequestMethod.GET)
				    @ResponseBody
				    public  ResponseEntity<Tipos> tiposById(@PathVariable String id) {
				    
				    try {
						Tipos tipos = allTiposRepository.findById(Long.parseLong(id)).get();
								    	
				    	return new ResponseEntity<Tipos>(tipos, HttpStatus.OK);
				    } catch (Exception e) {
					      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				    
				    }
				    
				    @PostMapping(value = "tipos") //mapeia a url
				    @ResponseBody //descrição da resposta
				    public ResponseEntity<Tipos> salvarTipos(@RequestBody Tipos tipos) { //Reebe os dados para salvar
				    try {
				    	Tipos chamado = allTiposRepository.save(tipos);
				    	
				    	return new ResponseEntity<Tipos>(chamado, HttpStatus.CREATED);
				    } catch (Exception e) {
					      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				    
				    }
				    
				    @RequestMapping(value = "/tipos/{id}", method = RequestMethod.PUT)
				    @ResponseBody
				    public  ResponseEntity<?> tiposUpdateById(@PathVariable String id, @RequestBody Tipos tipos) {
				    
				    try {
				    	
				    	if(tipos.getId() == null) {
				    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
				    	}
				    	
						Tipos chamado = allTiposRepository.saveAndFlush(tipos);
								    	
				    	return new ResponseEntity<Tipos>(chamado, HttpStatus.OK);
				    } catch (Exception e) {
					      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				    
				    }
				    
				    @RequestMapping(value = "/tipos/{id}", method = RequestMethod.DELETE)
				    @ResponseBody
				    public  ResponseEntity<String> tiposDeleteById(@PathVariable String id) {
				    
				    try {
						allTiposRepository.deleteById(Long.parseLong(id));
								    	
				    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
				    } catch (Exception e) {
					      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				    
				    }
 
   		    
	/***************************************************************************************************/
	    
	  	
	 @GetMapping(value = "buscaruserid") //mapeia a rede
	 @ResponseBody //Descrição da resposta
	 public ResponseEntity<Suporte> buscaruserid(@RequestParam(name = "iduser") Long iduser) { //recebe os dados para consultar
			
		Suporte suporte = userRepository.findById(iduser).get();
			
		return new ResponseEntity<Suporte>(suporte, HttpStatus.OK);
			
	}
	    
    
	@GetMapping(value = "listatodos") //Nosso primeiro método de API
    @ResponseBody //Retorna os dados para o corpo da resposta
    public ResponseEntity<List<Suporte>> listaUsuario() {
    	List<Suporte> chamados = userRepository.findAll(); //Executa a consulta no banco de dados
    	
    	return new ResponseEntity<List<Suporte>>(chamados, HttpStatus.OK);
    }
    
    @PostMapping(value = "salvar") //mapeia a url
    @ResponseBody //descrição da resposta
    public ResponseEntity<Suporte> salvar(@RequestBody Suporte suporte) { //Reebe os dados para salvar
    
    	Suporte chamado = userRepository.save(suporte);
    	
    	return new ResponseEntity<Suporte>(chamado, HttpStatus.CREATED);
    	}
    	
    @DeleteMapping(value = "delete") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<String> delete(@RequestParam Long iduser) { //Recebe os dados para delete
    
    	userRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
    	}
  
    @PutMapping(value = "atualizar") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<?> atualizar(@RequestBody Suporte suporte) { //Recebe os dados para salvar
    
    	if(suporte.getId() == null) {
    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
    	}
    	
    	Suporte chamado = userRepository.saveAndFlush(suporte);
    	
    	return new ResponseEntity<Suporte>(chamado, HttpStatus.OK);
    
    }
    
    @GetMapping(value = "buscarPorNome") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<List<Suporte>> buscarPorNome(@RequestParam(name = "name") String name) { //Recebe os dados para consultar
    
    	List<Suporte> suporte = userRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Suporte>>(suporte, HttpStatus.OK);
    
    }
    
    @GetMapping(value = "buscarPorData") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<List<Suporte>> buscarPorData(@RequestParam(name = "data") String data) { //Recebe os dados para consultar
    
    	List<Suporte> suporte = dataRepository.buscarPorData(data.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Suporte>>(suporte, HttpStatus.OK);
    
    }
    
    @GetMapping(value = "buscarPorPeriodo") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<List<Suporte>> buscarPorPeriodo(@RequestParam(name = "data") String data, @RequestParam(name = "data2") String data2) { //Recebe os dados para consultar
    
    	List<Suporte> suporte = periodoRepository.buscarPorPeriodo(data.trim().toUpperCase(), data2.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Suporte>>(suporte, HttpStatus.OK);
    
    }
    
    @GetMapping(value = "buscarPorGlpi") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<List<Suporte>> buscarPorGlpi(@RequestParam(name = "glpi") String glpi) { //Recebe os dados para consultar
    
    	List<Suporte> suporte = glpiRepository.buscarPorGlpi(glpi.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Suporte>>(suporte, HttpStatus.OK);
    
    }
    
    @GetMapping(value = "buscarPorTipo") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<List<Suporte>> buscarPorTipo(@RequestParam(name = "tipo") String tipo) { //Recebe os dados para consultar
    
    	List<Suporte> suporte = tipoRepository.buscarPorTipo(tipo.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Suporte>>(suporte, HttpStatus.OK);
    
    }
    
    @GetMapping(value = "buscarPorDescricao") //mapeia a url
    @ResponseBody //Descrição da resposta
    public ResponseEntity<List<Suporte>> buscarPorDescricao(@RequestParam(name = "descricao") String descricao) { //Recebe os dados para consultar
    
    	List<Suporte> suporte = descricaoRepository.buscarPorDescricao(descricao.toUpperCase());
    	
    	return new ResponseEntity<List<Suporte>>(suporte, HttpStatus.OK);
    
    }
    
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Spring Dev " + name + "!";
    }
 
	
	  @RequestMapping(value = "/selenium", method = RequestMethod.GET)
	  @ResponseStatus(HttpStatus.OK) public String greetingText(
			  @RequestParam(name = "tipo") String tipo,
			  @RequestParam(name = "mensagem") String mensagem,
			  @RequestParam(name = "requerente") String requerente) 
	  		{
	  
	  
		  		ScraperService scrapper = new ScraperService();
		  		scrapper.scrape(tipo,mensagem,requerente);
	  
		  		return "Selenium win!!"; 
		  	}	  

    
}


