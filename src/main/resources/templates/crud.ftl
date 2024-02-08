<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Crud Cidades</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container-fluid">
        <div class="jumbotron mt-5">
            <h1>GERENCIAMENTO DE CIDADES</h1>
            <p>UM CRUD PARA CRIAR, ALTERAR, EXCLUIR E LISTAR CIDADES</p>
        </div>
        <#if cidadeAtual??>
            <form action="/alterar" method="POST" class="needs-validation" novalidate>
            <input type="hidden" name="nomeAtual" value="${(cidadeAtual.nome)!}" />
            <input type="hidden" name="estadoAtual" value="${(cidadeAtual.estado)!}" />
        <#else>
            <form action="/criar" method="POST" class="needs-validation" novalidate>
        </#if>
            <div class="form-group">
                <label for="nome">Cidade:</label>
                <input
                    value="${(cidadeAtual.nome)!}${nomeInformado!}"
                    name="nome"
                    type="text"
                    class="form-control ${(nome??)?then('is-invalid', '')}"
                    id="nome"
                    placeholder="Informe o nome da cidade">
                <div class="invalid-feedback">
                    ${nome!}
                </div>
            </div>
            <div class="form-group">
                <label for="estado">Estado:</label>
                <input
                    value="${(cidadeAtual.estado)!}${estadoInformado!}"
                    name="estado"
                    type="text"
                    class="form-control ${(estado??)?then('is-invalid', '')}"
                    id="estado"
                    placeholder="Informe o estado ao qual a cidade pertence">
                <div class="invalid-feedback">
                    ${estado!}
                </div>
            </div>
            <#if cidadeAtual??>
                <button type="submit" class="btn btn-warning">Concluir Alteração</button>
            <#else>
                <button type="submit" class="btn btn-primary">Criar</button>
            </#if>
        </form>
        <table class="table table-striped table-hover mt-5">
            <thead class="thead-dark">
                <tr>
                    <th>Nome</th>
                    <th>Estado</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <#list listaCidades as cidade>
                <tr>
                    <td>${cidade.nome}</td>
                    <td>${cidade.estado}</td>
                    <td>
                        <div class="d-flex d-justify-content-center">
                            <a href="/preparaAlterar?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-warning mr-3">Alterar</a>
                            <a href="/excluir?nome=${cidade.nome}&estado=${cidade.estado}" class="btn btn-danger mr-3">Excluir</a>
                        </div>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</body>
</html>