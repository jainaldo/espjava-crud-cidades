package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class CidadeController {

    private final CidadeRepository repository;

    CidadeController(CidadeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listar(Model memoria) {
        memoria.addAttribute("listaCidades", repository.findAll()
                .stream()
                .map(cidade -> new Cidade(
                        cidade.getNome(),
                        cidade.getEstado())
                ).collect(Collectors.toList()));

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
            memoria.addAttribute("listaCidades", repository.findAll()
                    .stream()
                    .map(cidadeEntidade -> new Cidade(
                            cidadeEntidade.getNome(),
                            cidadeEntidade.getEstado())
                    ).collect(Collectors.toList()));

            return ("/crud");
        } else {
            repository.save(cidade.clonar());
        }

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(
            @RequestParam String nome,
            @RequestParam String estado) {

        var cidadeEstadoEncontrada = repository.findByNomeAndEstado(nome, estado);

        cidadeEstadoEncontrada.ifPresent(repository::delete);

        return "redirect:/";
    }

    @GetMapping("/preparaAlterar")
    public String preparaAlterar(
            @RequestParam String nome,
            @RequestParam String estado,
            Model memoria) {

        var cidadeAtual = repository.findByNomeAndEstado(nome, estado);

        cidadeAtual.ifPresent(cidadeEncontrada -> {
            memoria.addAttribute("cidadeAtual", cidadeEncontrada);
            memoria.addAttribute("listaCidades", repository.findAll()
                    .stream()
                    .map(cidadeEntidade -> new Cidade(
                            cidadeEntidade.getNome(),
                            cidadeEntidade.getEstado())
                    ).collect(Collectors.toList()));
        });

        return "/crud";
    }

    @PostMapping("/alterar")
    public String alterar(
            @RequestParam String nomeAtual,
            @RequestParam String estadoAtual,
            Cidade cidade) {

        var cidadeAtual = repository.findByNomeAndEstado(nomeAtual, estadoAtual);

        cidadeAtual.ifPresent(cidadeEncontrada -> {
            cidadeEncontrada.setNome(cidade.getNome());
            cidadeEncontrada.setEstado(cidade.getEstado());

            repository.saveAndFlush(cidadeEncontrada);
        });

        return "redirect:/";
    }
}
