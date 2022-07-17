package com.albusoft.financas.mensagens;

public class Mensagem {

	//Erros e validações
	public static final String USUARIO_NAO_EXISTE = "O usuário não existe.";
	public static final String EMAIL_EXISTE = "Já existe um usuário no sistema com este email.";
	public static final String EMAIL_USUARIO_NAO_ENCONTRADO = "Nenhum usuário encontrado com o email passado.";
	public static final String SENHA_INVALIDA = "Senha inválida.";
	public static final String DESCRICAO_LANCAMENTO_VAZIO = "O campo de descrição não pode estar vazio.";
	public static final String MES_LANCAMENTO_INVALIDO = "Digite um mês válido para o lançamento.";
	public static final String ANO_LANCAMENTO_INVALIDO = "Digite um ano válido para o lançamento.";
	public static final String USUARIO_LANCAMENTO_INVALIDO = "Informe um usuário para o lançamento.";
	public static final String VALOR_MENOR_OU_IGUAL_A_ZERO = "Informe um valor maior que zero.";
	public static final String TIPO_LANCAMENTO_INVALIDO = "Tipo de lançamento não informado.";
	public static final String ERRO_AO_SALVAR_LANCAMENTO = "Erro ao salvar lançamento.";
	public static final String LANCAMENTO_NAO_ENCONTRADO = "Lançamento não encontrado";
	public static final String ERRO_AO_DELETAR_LANCAMENTO = "Erro ao deletar o lançamento.";
	public static final String ERRO_AO_ATUALIZAR_STATUS_LANCAMENTO = "Não foi possível atualizar o status do"
			+ " lançamento. Verifique se o status passado é um status válido.";
	
}
