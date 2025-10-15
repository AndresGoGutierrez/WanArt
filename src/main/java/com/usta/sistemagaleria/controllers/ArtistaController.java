package com.usta.sistemagaleria.controllers;

import com.usta.sistemagaleria.entities.ArtistaEntity;
import com.usta.sistemagaleria.entities.ObraEntity;
import com.usta.sistemagaleria.models.services.ArtistaService;
import com.usta.sistemagaleria.models.services.ObraService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;
    @Autowired
    private ObraService obraService;
    @GetMapping(value = "/artista")
    public String listarL(Model model,
                          @RequestParam(value = "filterType", required = false)String filterType,
                          @RequestParam(value = "searchTerm", required = false)String searchTerm) {
        model.addAttribute("title", "Artistas");
        model.addAttribute("urlRegistro", "/crearArtista");

        List<ArtistaEntity> artistas = new ArrayList<>();
        if (searchTerm != null && !searchTerm.isEmpty()) {
            if ("artista".equals(filterType)) {
                artistas = artistaService.findByArtistaNombre(searchTerm);
            } else if ("disciplina".equals(filterType)) {
                artistas = artistaService.findByArtistaDisciplina(searchTerm);
            } else if ("genero".equals(filterType)) {
                artistas = artistaService.findByArtistaGenero(searchTerm);
            }
        } else {
            artistas = artistaService.findAll();
        }

        model.addAttribute("artistas", artistas);
        model.addAttribute("filterType", filterType);
        model.addAttribute("searchTerm", searchTerm);
        return "artistas/listarArtista";
    }

    /*------------------------------------------------------------------------- */
    @RequestMapping(value = "/detalleArtista/{id}")
    public String detalleArtista(Model model, @PathVariable(value = "id") Long idArtista) {
        model.addAttribute("title", "Artista");
        model.addAttribute("detalleA", artistaService.viewDetail(idArtista));
        model.addAttribute("obras", obraService.findByObraByArtistaId(idArtista));
        return "artistas/detalleArtista";
    }

    /*------------------------------------------------------------------------- */

    private String guardarImagen(MultipartFile imagen) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.imgbb.com/1/upload");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("key", "4ba86690d1318c201abdbb8d2b72bfce", ContentType.TEXT_PLAIN);
            builder.addBinaryBody("image", imagen.getInputStream(),
                    ContentType.DEFAULT_BINARY, imagen.getOriginalFilename());
            HttpEntity multipart = (HttpEntity) builder.build();
            httpPost.setEntity(multipart);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                String resposeString = EntityUtils.toString(responseEntity);
                JSONObject jsonResponse = new JSONObject(resposeString);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    JSONObject data = jsonResponse.getJSONObject("data");
                    String imageUrl = data.getString("url");
                    return imageUrl;
                }else {
                    String errorMessage = jsonResponse.optString("Error desconocido");
                    System.out.println("Error al cargar la imagen ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*------------------------------------------------------------------------- */

    @GetMapping(value = "/crearArtista")
    public String ListarFormArtista(Model model) {
        model.addAttribute("title", "Registro artistas");
        model.addAttribute("artista", new ArtistaEntity());
        return "artistas/crearArtista";
    }

    /*------------------------------------------------------------------------- */

    @PostMapping(value = "/crearArtista")
    public String guardarLibro(@Valid ArtistaEntity artista,
                               @RequestParam(value = "foto") MultipartFile foto,
                               BindingResult result) {
        String nombreImagen = guardarImagen(foto);
        if (result.hasErrors()) {
            return "error500";
        }
        artista.setFotoArtista(nombreImagen);
        artistaService.save(artista);
        return "redirect:/artista";
    }

    /*------------------------------------------------------------------------- */

    @RequestMapping(value = "/eliminarArtista/{id}")
    public String eliminarById(@PathVariable(value = "id") Long id) throws IOException {
        if (id>0){
            ArtistaEntity artista = artistaService.findById(id);
            if (artista != null){
                artistaService.save(artista);
                artistaService.deleteById(id);
            }
        } else {
            return "error500";
        }
        return "redirect:/artista";
    }

    /*------------------------------------------------------------------------- */

    @GetMapping(value = "/editarArtista/{id}")
    public String editarArtista(Model model, @PathVariable(value = "id") Long idArtista) {
        model.addAttribute("title", "Editar Artista");
        model.addAttribute("artistas", artistaService.findById(idArtista));
        model.addAttribute("imagen", artistaService.viewDetail(idArtista).getFotoArtista());

        return "artistas/editarArtista";
    }

    /*------------------------------------------------------------------------- */

    @PostMapping (value = "/editarArtista/{id}")
    public String editArtista(@ModelAttribute("artistaEdit") ArtistaEntity artistaEntity,
                              @PathVariable(value = "id") Long idArtista,
                              @RequestParam(value = "foto") MultipartFile foto,
                              BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "error500";
        }

        ArtistaEntity artistaAuxiliar = artistaService.findById(idArtista);
        artistaAuxiliar.setIdArtista(idArtista);
        artistaAuxiliar.setNombreArt(artistaEntity.getNombreArt());
        artistaAuxiliar.setApellidoArt(artistaEntity.getApellidoArt());
        artistaAuxiliar.setNacionalidadArt(artistaEntity.getNacionalidadArt());
        artistaAuxiliar.setFechaNacimiento(artistaEntity.getFechaNacimiento());
        artistaAuxiliar.setDisciplinaArtistica(artistaEntity.getDisciplinaArtistica());
        artistaAuxiliar.setGeneroArtistico(artistaEntity.getGeneroArtistico());
        artistaAuxiliar.setRedSocial(artistaEntity.getRedSocial());

        String imagen = artistaAuxiliar.getFotoArtista();
        String nombreImagen = guardarImagen(foto);
        if (nombreImagen.isBlank() || nombreImagen.isEmpty() || nombreImagen == null) {
            artistaAuxiliar.setFotoArtista(imagen);
        } else {
            artistaAuxiliar.setFotoArtista(nombreImagen);
        }
        artistaService.actualizarArtistaEntity(artistaAuxiliar);
        return "redirect:/artista";
    }
}
