listarTabelaGeral(dataHojeInicio());

const inputDate = document.getElementById('dataInicial'); 
document.getElementById('dataInicial').value =  dataHojeInicio();

inputDate.addEventListener('input', function() {
	listarTabelaGeral(inputDate.value);
});

const inputElement = document.getElementById('nameBusca');
inputElement.addEventListener('keydown', function(event) {
	if (event.keyCode === 13) {
		event.preventDefault(); // Impede o envio padrão do formulário
		listarTabelaGeral('filtradaNaBusca');
	}
});

function gerarRecibo() {
	document.getElementById('recibo_emitente').innerHTML = "William Alefe Lucas Teixeira";
	window.print();
}


function adicionarZero(numero) {
	return numero < 10 ? "0" + numero : numero;
}
function alterardata() {
	const dataAtual = new Date();
	const horas = adicionarZero(dataAtual.getHours());
	const minutos = adicionarZero(dataAtual.getMinutes());
	const horarioAtual = horas + ":" + minutos;
}

function converterFormato(dataOriginal) {
	const partes = dataOriginal.split('-');
	if (partes.length === 3) {
		const ano = partes[0];
		const mes = partes[1];
		const dia = partes[2];
		return `${dia}/${mes}/${ano}`;
	} else {
		return null; // Retorna null se o formato original for inválido
	}
}

function botaoDeletarDaTela() {
	var id = $('#id').val();

	if (id != null && id.trim() != '') {
		deleteUser(id);/*
		document.getElementById('formCadastroUser').reset();*/
		desaparecersalvar();
		console.log("Deletado com sucesso: " + id)
	} else {
		console.log("Id vazio")
	}

}

function deleteUser(id) {

	if (confirm('Deseja realmente deletar?')) {

		$.ajax({
			method: "DELETE",
			url: "delete",
			data: "iduser=" + id,
			success: function(response) {

				$('#' + id).remove();

				removerLinha(id)
				alert(response);
			}
		}).fail(
			function(xhr, status, errorThrown) {
				alert("Erro ao deletar usuario por id: "
					+ xhr.responseText);
			});

	}

}

function removerLinha(id) {
	var linha = document.getElementById(id);
	if (linha) {
		linha.parentNode.removeChild(linha);
	} else {
		console.error('Linha não encontrada com o ID:', id);
	}
}

///////////////////////////////FUNCOES DE PESQUISA////////////////////////////////////////////////////

function listarTabelaGeral(tipoDeListagem) {
	let urlData = "";
	let nameData = "";
	let nomeNaBusca = "";
	let botaoDePesquisa = "";
	let textoPesquisa = "";
	let dataInicial = "";
	let dataFinal = "";

	let descricao = document.getElementById("descricaoImpressao");

	if (tipoDeListagem == "todos") {

		$("#dataInicial").val("");
		urlData = "listatodos";
		
		descricao.textContent = "Todos os Serviços efetuados na UpSobral"

	} else if (tipoDeListagem == "filtradaNaBusca") {

		nomeNaBusca = $('#nameBusca').val();

		if (nomeNaBusca != null && nomeNaBusca.trim() != '') {
			botaoDePesquisa = document.getElementById("selecionapesquisa");
			textoPesquisa = botaoDePesquisa.textContent;

			if (textoPesquisa == "Requerente") {
				urlData = "buscarPorNome";
				nameData = "name=";
			} else if (textoPesquisa == "Tipo") {
				urlData = "buscarPorTipo";
				nameData = "tipo=";
			} else if (textoPesquisa == "Glpi") {
				urlData = "buscarPorGlpi";
				nameData = "glpi=";
			} else if (textoPesquisa == "Descricao") {
				urlData = "buscarPorDescricao";
				nameData = "descricao=";
			}

		} else {
			$("#dataInicial").val("");
			urlData = "listatodos";
		}


	} else if (tipoDeListagem == "mensal") {

		let data = new Date();
		let mes = adicionarZero((data.getMonth() + 1));
		let ano = data.getFullYear();

		dataInicial = ano + "-" + mes + "-01";
		dataFinal = ano + "-" + mes + "-32";

		urlData = 'buscarPorPeriodo?data=' + dataInicial + '&data2=' + dataFinal;

		descricao.textContent = "Serviços efetuados na UpSobral durante o mês " + mes


	} else if (tipoDeListagem == "semanal") {
		let data = new Date();
		let ano = data.getFullYear();
		let semana = adicionarZero(data.getDate());
		let mes = adicionarZero((data.getMonth() + 1));

		dataFinal = ano + "-" + mes + "-" + semana;
		semana = adicionarZero(semana - 7);
		dataInicial = ano + "-" + mes + "-" + semana;

		urlData = 'buscarPorPeriodo?data=' + dataInicial + '&data2=' + dataFinal;

		descricao.textContent = "Serviços efetuados na UpSobral no período de " + converterFormato(dataInicial) + " à " + converterFormato(dataFinal)

	} else {

		if (tipoDeListagem != null && tipoDeListagem.trim() != '') {
			urlData = "buscarPorData";
			nameData = "data=" + tipoDeListagem;
			descricao.textContent = "Serviços efetuados na UpSobral na data de " + converterFormato(tipoDeListagem);
		}
	}

	$
		.ajax(
			{
				method: "GET",
				url: urlData,
				data: nameData + nomeNaBusca,
				success: function(response) {
					
					$('#tabelaresultadostodos > tbody > tr')
						.remove();

					$('#tabelaimpressa > tbody > tr')
						.remove();

					for (var i = 0; i < response.length; i++) {
						let nomeChamado = "";
						let dataConvertida = "";

						nomeChamado = converterNomeChamado(response[i]);

						if (response[i].dataDoChamado != null) {
							dataConvertida = converterFormato(response[i].dataDoChamado)
						} else {
							dataConvertida = "00/00/00"
						}

						$('#tabelaresultadostodos > tbody')
							.append(
								'<tr id="' + response[i].id + '"><td>'
								+ (i + 1)
								+ '</td><td>'
								+ nomeChamado
								+ '</td><td>'
								+ response[i].descricaoDoChamado
								+ '</td><td>'
								+ response[i].requerente
								+ '</td><td>'
								+ response[i].horarioDeAberturaDoChamado.substring(0, 5)
								+ '</td><td>'
								+ dataConvertida
								+ '</td><td>'
								+ response[i].chamadoGlpi
								+ '</td><td><button type="button" onclick="verchamado('
								+ response[i].id
								+ ')" class="btn btn-primary btn-lg">Ver</button></td></tr>');

						listarTabelaImpressao(response[i], (i + 1), nomeChamado, dataConvertida);
					}
					
					if(response.length == 0 ){
						SeZerarPesquisa();
					}

				}
			}).fail(
				function(xhr, status, errorThrown) {
					alert("Erro ao filtrar: "
						+ xhr.responseText);
				});

}

function listarTabelaImpressao(response, i, nomeChamado, dataConvertida) {
	$('#tabelaimpressa > tbody')
		.append(
			'<tr id="' + response.id + '"><td>'
			+ i
			+ '</td><td>'
			+ nomeChamado
			+ '</td><td>'
			+ response.descricaoDoChamado
			+ '</td><td>'
			+ response.requerente
			+ '</td><td>'
			+ response.horarioDeAberturaDoChamado.substring(0, 5)
			+ '</td><td>'
			+ dataConvertida
			+ '</td><td>'
			+ response.chamadoGlpi
			+ '</td><td></td></tr>');

}

function SeZerarPesquisa(){
	$('#tabelaresultadostodos > tbody')
		.append(
			'<tr id="' + 0 + '"><td>'
			+ ''
			+ '</td><td>'
			+ ''
			+ '</td><td>'
			+ ''
			+ '</td><td>'
			+ 'Sem resultados'
			+ '</td><td>'
			+ ''
			+ '</td><td>'
			+ ''
			+ '</td><td>'
			+ ''
			+ '</td><td></td></tr>');	
}

function converterNomeChamado(response) {
	let nomeChamado = "";

	if (response.tipoDeChamado == "1") {
		nomeChamado = "Hardware/Software"
	} else if (response.tipoDeChamado == "2") {
		nomeChamado = "CFTV"
	} else if (response.tipoDeChamado == "3") {
		nomeChamado = "Impressora"
	} else if (response.tipoDeChamado == "4") {
		nomeChamado = "Telefonia"
	} else if (response.tipoDeChamado == "5") {
		nomeChamado = "Solicitação de material"
	} else {
		nomeChamado = "Outro"
	}
	return nomeChamado;
}



//////////////////ACOES JQUERY/////////////////////////////////

function editarChamado(id) {
	const selector = document.querySelector("#tipo");
	$.ajax({
		method: "GET",
		url: "buscaruserid",
		data: "iduser=" + id,
		success: function(response) {

			$("#id").val(response.id);
			selector.value = response.tipoDeChamado;
			$("#descricao").val(response.descricaoDoChamado);
			$("#requerente").val(response.requerente);
			$("#horario").val(response.horarioDeAberturaDoChamado);
			$("#data").val(response.dataDoChamado);
			$("#glpi").val(response.chamadoGlpi)
		}
	}).fail(function(xhr, status, errorThrown) {
		alert("Erro ao buscar usuario por id: " + xhr.responseText);
	});

}

function teste(){

	console.log(botao.textContent)
	console.log("OK")
}

function salvarUsuario() {
	let botao = document.getElementById('botaoPost');
	var select = document.getElementById('tipo')

	var id = $("#id").val();
	var tipo = parseInt(select.value);
	var descricao = $("#descricao").val();
	var requerente = $("#requerente").val();
	var glpi = $("#glpi").val()
	var horario = $("#horario").val();
	var data = $("#data").val();

	if (descricao == null || descricao != null && descricao.trim() == '') {
		$("#descricao").focus();
		alert('Informe a descricao');
		return;
	}

	$.ajax({
		method: "POST",
		url: "salvar",
		data: JSON.stringify({
			id: id,
			tipoDeChamado: tipo,
			descricaoDoChamado: descricao,
			requerente: requerente,
			horarioDeAberturaDoChamado: horario,
			dataDoChamado: data,
			chamadoGlpi: glpi
		}),
		contentType: "application/json; charset=utf-8",
		success: function(response) {
			if(botao.textContent == "Alterar"){
				
				alert("Alterou com sucesso!");
				document.getElementById('formCadastroUser').reset();	
				desaparecersalvar();
				
			}else{
				$("#id").val(response.id);
				alert("Gravou com sucesso!");
				document.getElementById('formCadastroUser').reset();
				setardata();
				document.querySelector("#tipo").value = "1";
			}
		}
	}).fail(function(xhr, status, errorThrown) {
		alert("Erro ao salvar usuario: " + xhr.responseText);
	});

}


function aparecersalvar() {
	document.getElementById('formCadastroUser').reset();
	document.querySelector('.painelebutton').style.display = "grid";
	document.querySelector("#tela1").style.display = "none";
	document.querySelector(".container").style.display = "none";
	document.querySelector("#tipo").value = "1";
	document.getElementById('deletarbotao').style.display = "none";
	setardata();
	document.getElementById('botaoPost').textContent = "Salvar";
}
function desaparecersalvar() {
	document.querySelector("#aparecerid").style.display = "none";
	document.querySelector('.painelebutton').style.display = "none";
	document.querySelector("#tela1").style.display = "grid";
	document.querySelector(".container").style.display = "grid";
	if(document.getElementById('botaoPost').textContent == "Salvar"){
		listarTabelaGeral(dataHojeInicio());
	}

}

function verchamado(idatual) {
	aparecersalvar();
	document.querySelector("#aparecerid").style.display = "grid";
	document.getElementById('deletarbotao').style.display = "grid";
	editarChamado(idatual);
	document.getElementById('botaoPost').textContent = "Alterar";
}


function definirPesquisa(tipoDePesquisa) {

	let botaoDaPesquisa = document.getElementById("selecionapesquisa");
	document.getElementById('dataInicial').value = "";

	if (tipoDePesquisa == "tipo") {

		botaoDaPesquisa.textContent = "Tipo";

	} else if (tipoDePesquisa == "glpi") {

		botaoDaPesquisa.textContent = "Glpi";

	} else if (tipoDePesquisa == "requerente") {

		botaoDaPesquisa.textContent = "Requerente";

	} else {

		botaoDaPesquisa.textContent = "Descricao";

	}
}

////////////////////////////////////DATAS//////////////

function dataHojeInicio() {
	let data = new Date();
	return data.getFullYear() + "-" + adicionarZero(data.getMonth() + 1) + "-"
		+ adicionarZero(data.getDate());
}

function setardata() {
	dataAtual = new Date().toLocaleDateString();

	horarioAtual = new Date().toLocaleTimeString();

	let dataConvertida = converterData(dataAtual);

	$("#horario").val(horarioAtual);
	$("#data").val(dataConvertida);

}

function converterData(data) {
	const partes = data.split('/');
	if (partes.length !== 3) {
		return 'Formato de data inválido. Use "dd/MM/yyyy".';
	}

	const [dia, mes, ano] = partes;
	const dataFormatada = `${ano}-${mes.padStart(2, '0')}-${dia.padStart(2, '0')}`;
	return dataFormatada;
}



/////////////////////SELENIUM////////////////////////////////////////////////

function seleniumativar() {
	
	let urlData = "";
	let select = document.getElementById('tipo')

	let tipo = select.value;
	let descricao = $("#descricao").val();
	let requerente = $("#requerente").val();
	
	urlData     = 'selenium?tipo=' + tipo + '&mensagem=' + descricao
					+'&requerente='+requerente;
	
	$.ajax({
		method: "GET",
		url: urlData,
		data: "",
		success: function(response) {
			alert("Selenium win!");
		}
	}).fail(function(xhr, status, errorThrown) {
		alert("Erro ao selenizar usuario: " + xhr.responseText);
	});

}
////////////////////////////////////////////////////////////////////////////////

