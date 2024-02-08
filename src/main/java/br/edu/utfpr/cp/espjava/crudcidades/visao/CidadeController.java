package br.edu.utfpr.cp.espjava.crudcidades.visao;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class CidadeController {

    private Set<Cidade> cidades;

    CidadeController() {
        cidades = new HashSet<>();
    }

    @GetMapping("/")
    public String listar(Model memoria) {
        memoria.addAttribute("listaCidades", cidades);

        return "/crud";
    }
    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria) {
        if (validacao.hasErrors()) {
            validacao
                    .getFieldErrors()
                    .forEach(error ->
                            memoria.addAttribute(
                                    error.getField(),
                                    error.getDefaultMessage()
                            )
                    );
            memoria.addAttribute("nomeInformado", cidade.getNome());
            memoria.addAttribute("estadoInformado", cidade.getEstado());
            memoria.addAttribute("listaCidades", cidades);
            return ("/crud");
        } else {
            cidades.add(cidade);
        }

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(
            @RequestParam String nome,
            @RequestParam String estado) {

        cidades.removeIf(c ->
                c.getNome().equals(nome) &&
                c.getEstado().equals(estado));

        return "redirect:/";
    }

    @GetMapping("/preparaAlterar")
    public String preparaAlterar(
            @RequestParam String nome,
            @RequestParam String estado,
            Model memoria) {

        var cidadeAtual = cidades
                .stream()
                .filter(c ->
                        c.getNome().equals(nome) &&
                                c.getEstado().equals(estado))
                .findAny();

        if (cidadeAtual.isPresent()) {
            memoria.addAttribute("cidadeAtual", cidadeAtual.get());
            memoria.addAttribute("listaCidades", cidades);
        }

        return "/crud";
    }

    @PostMapping("/alterar")
    public String alterar(
            @RequestParam String nomeAtual,
            @RequestParam String estadoAtual,
            Cidade cidade,
            BindingResult validacao,
            Model memoria) {

        cidades.removeIf(c ->
                c.getNome().equals(nomeAtual) &&
                c.getEstado().equals(estadoAtual));

        criar(cidade, validacao, memoria);

        return "redirect:/";
    }
}
