<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Atualização de Produto</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<div class="container">
		<br>
		<h1>Edição de Produto</h1>
		<form action="produto" method="post">
			<input type="hidden" value="editar" name="acao"> <input
				type="hidden" value="${produto.codigo}" name="codigo">
			<div class="form-group">
				<label for="id-nome">Nome</label> <input type="text" name="nome"
					id="id-nome" class="form-control" value="${produto.nome }">
			</div>
			<div class="form-group">
				<label for="id-valor">Valor</label> <input type="text" name="valor"
					id="id-valor" class="form-control" value="${produto.valor }">
			</div>
			<div class="form-group">
				<label for="id-quantidade">Quantidade</label> <input type="text"
					name="quantidade" id="id-quantidade" class="form-control"
					value="${produto.quantidade }">
			</div>
			<div class="form-group">
				<label for="id-fabricacao">Data de Fabricação</label> <input
					type="text" name="fabricacao" id="id-fabricacao"
					class="form-control"
					value='<fmt:formatDate value="${produto.dataFabricacao.time }" pattern="dd/MM/yyyy"/>'>
			</div>
			<div class="form-group">
				<label for="id-categoria">Categoria</label> <select name="categoria"
					id="id-categoria" class="form-control">
					<option value="0">Selecione</option>
					<c:forEach items="${categorias }" var="c">
						<c:if test="${c.codigo == produto.categoria.codigo }">
							<option value="${c.codigo }" selected>${c.nome }</option>
						</c:if>
						<c:if test="${c.codigo != produto.categoria.codigo }">
							<option value="${c.codigo }">${c.nome }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="id-marca">Marca</label> <select name="marca"
					id="id-marca" class="form-control">
					<option value="0">Selecione</option>
					<c:forEach items="${marcas }" var="m">
						<c:if test="${m.id == produto.marca.id }">
							<option value="${m.id }" selected>${m.nome }</option>
						</c:if>
						<c:if test="${m.id != produto.marca.id}">
							<option value="${m.id }">${m.nome }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
			<br> <input type="submit" value="Salvar" class="btn btn-primary">
			<a href="produto?acao=listar" class="btn btn-danger">Cancelar</a>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>
