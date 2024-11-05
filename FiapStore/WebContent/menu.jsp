<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="home.jsp">FIAPStore</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link"
				href="produto?acao=abrir-form-cadastro">Cadastro</a></li>
			<li class="nav-item"><a class="nav-link"
				href="produto?acao=listar">Produtos</a></li>
		</ul>
		<c:if test="${empty user}">
			<span class="navbar-text text-danger" style="margin-left:auto" >
				${erro} </span>
			<form class="navbar-nav mr-auto" action="login" method="post">
				<input class="form-control mr-auto" type="text" name="email"
					placeholder="E-mail"> <input class="form-control mr-auto"
					type="password" name="senha" placeholder="Senha">
				<button class="btn btn-outline-success my-2 my-sm-auto"
					type="submit">Entrar</button>
			</form>
		</c:if>
		<c:if test="${not empty user }">
			<span class="navbar-text" style="margin-left:auto"> ${user} <a href="login"
				class="btn btn-outline-primary my-2 my-sm-auto">Sair</a>
			</span>
		</c:if>
	</div>
</nav>
