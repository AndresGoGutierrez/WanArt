package com.usta.sistemagaleria.controllers;

import com.usta.sistemagaleria.entities.ArtistaEntity;
import com.usta.sistemagaleria.entities.ExposicionEntity;
import com.usta.sistemagaleria.entities.ObraEntity;
import com.usta.sistemagaleria.models.services.ExposicionService;
import com.usta.sistemagaleria.models.services.ObraService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExposicionController {
    @Autowired
    private ExposicionService exposicionService;
    @Autowired
    private ObraService obraService;
    @GetMapping(value = "/exposicion")
    public String listarE(Model model,
                          @RequestParam(value = "filterType", required = false)String filterType,
                          @RequestParam(value = "searchTerm", required = false)String searchTerm) {
        model.addAttribute("title", "Exposiciones");
        model.addAttribute("urlRegistro", "/crearExposicion");

        List<ExposicionEntity> exposiciones = new ArrayList<>();
        if (searchTerm != null && !searchTerm.isEmpty()) {
            if ("nombreExpo".equals(filterType)) {
                exposiciones = exposicionService.findByExposicionTitulo(searchTerm);
            } else if ("annioInicio".equals(filterType)) {
                try {
                    long anno = Long.parseLong(searchTerm); // Conversión segura
                    exposiciones = exposicionService.findByObraAnno(anno);
                } catch (NumberFormatException e) {
                    exposiciones = new ArrayList<>(); // Evitar que devuelva resultados
                    model.addAttribute("error", "El año debe ser un número válido.");
                }
            } else if ("ubicacion".equals(filterType)) {
                exposiciones = exposicionService.findByExposicionUbicacion(searchTerm);
            }
        } else {
            exposiciones = exposicionService.findAll();
        }

        model.addAttribute("exposiciones", exposiciones);
        model.addAttribute("filterType", filterType);
        model.addAttribute("searchTerm", searchTerm);
        return "exposiciones/listarExposicion";
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


    /*----------------------------------------------------------------------------- */
    @GetMapping(value = "/crearExposicion")
    public String ListarFormExposicion(Model model) {
        model.addAttribute("title", "Registrar Exposición");
        model.addAttribute("exposiciones", new ExposicionEntity());
        List<ObraEntity> listaObras = obraService.findAll();
        if (listaObras == null){
            listaObras = new ArrayList<>();
        }
        model.addAttribute("listaObras", listaObras);
        return "exposiciones/crearExposicion";
    }

    /*------------------------------------------------------------------------- */

    @PostMapping(value = "/crearExposicion")
    public String guardarExposicion(@Valid ExposicionEntity exposicion,
                                    @RequestParam List<Long> obras,
                                    @RequestParam(value = "foto") MultipartFile foto,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return "error500";
        }
        String nombreImagen = guardarImagen(foto);
        exposicion.setFotoExpo(nombreImagen);

        List<ObraEntity> obrasSeleccionadas = new ArrayList<>();
        for (Long idObra : obras) {
            ObraEntity obra = obraService.findById(idObra);
            if (obra != null) {
                obrasSeleccionadas.add(obra);
            }
        }
        exposicion.setObras(obrasSeleccionadas);
        exposicionService.save(exposicion);
        return "redirect:/exposicion";
    }





    /*------------------------------------------------------------------------- */
    @RequestMapping(value = "/detalleExposicion/{id}")
    public String detalleExposicion(Model model, @PathVariable(value = "id") Long idExpo) {
        model.addAttribute("title", "Detalle Exposición");
        model.addAttribute("detalleE", exposicionService.viewDetail(idExpo));
        model.addAttribute("obras", obraService.findObrasByExposicionId(idExpo));
        return "exposiciones/detalleExposicion";
    }

    /*------------------------------------------------------------------------- */

    @RequestMapping(value = "/eliminarExposicion/{id}")
    public String eliminarById(@PathVariable(value = "id") Long id) throws IOException {
        if (id>0){
            ExposicionEntity exposicion = exposicionService.findById(id);
            if (exposicion != null){
                exposicion.getObras().clear();
                exposicionService.save(exposicion);
                exposicionService.deleteById(id);
            }
        } else {
            return "redirect:/error500";
        }
        return "redirect:/exposicion";
    }

    /*------------------------------------------------------------------------- */

    @GetMapping(value = "/editarExposicion/{id}")
    public String editarExposicion(Model model, @PathVariable(value = "id") Long idExpo) {
        model.addAttribute("title", "Editar Exposición");
        model.addAttribute("exposiciones", exposicionService.findById(idExpo));
        model.addAttribute("imagen", exposicionService.viewDetail(idExpo).getFotoExpo());
        List<ObraEntity> obras = obraService.findAll();
        if (obras == null){
            obras = new ArrayList<>();
        }
        model.addAttribute("obras", obras);
        return "exposiciones/editarExposicion";
    }

    /*------------------------------------------------------------------------- */

    @PostMapping (value = "/editarExposicion/{id}")
    public String editExposicion(@ModelAttribute("exposicionEdit") ExposicionEntity exposicionEntity,
                                 @PathVariable(value = "id") Long idExpo,
                                 @RequestParam(value = "obras") List<Long> obrasIds,
                                 @RequestParam(value = "foto") MultipartFile foto,
                                 BindingResult result) throws IOException {
        if(result.hasErrors()) {
            return "error500";
        }
        ExposicionEntity exposicionAuxiliar = exposicionService.findById(idExpo);
        exposicionAuxiliar.setIdExpo(idExpo);
        exposicionAuxiliar.setTituloExpo(exposicionEntity.getTituloExpo());
        exposicionAuxiliar.setDescripcionExpo(exposicionEntity.getDescripcionExpo());
        exposicionAuxiliar.setFechaInicio(exposicionEntity.getFechaInicio());
        exposicionAuxiliar.setFechaFin(exposicionEntity.getFechaFin());
        exposicionAuxiliar.setAforo(exposicionEntity.getAforo());
        exposicionAuxiliar.setUbicacion(exposicionEntity.getUbicacion());
        exposicionAuxiliar.setObras(exposicionEntity.getObras());

        String imagen = exposicionAuxiliar.getFotoExpo();
        String nombreImagen = guardarImagen(foto);
        if (nombreImagen.isBlank() || nombreImagen.isEmpty() || nombreImagen == null) {
            exposicionAuxiliar.setFotoExpo(imagen);
        } else {
            exposicionAuxiliar.setFotoExpo(nombreImagen);
        }

        exposicionAuxiliar.getObras().clear();
        if (obrasIds != null){
            for (Long obraId : obrasIds) {
                ObraEntity obra = obraService.findById(obraId);
                if (obra != null){
                    exposicionAuxiliar.getObras().add(obra);
                }
            }
        }

        exposicionService.actualizarExposicionEntity(exposicionAuxiliar);
        return "redirect:/exposicion";
    }



}
