package com.kappacomputacion.spring.ms.laws.services;

import com.kappacomputacion.spring.ms.laws.dtos.TextoLeyDto;
import com.kappacomputacion.spring.ms.laws.dtos.TextoLeyTreeUiDto;
import com.kappacomputacion.spring.ms.laws.dtos.TextoLeyUpdateDto;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.LawContentChild;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.LawNode;
import com.kappacomputacion.spring.ms.laws.entities.LawsMain;
import com.kappacomputacion.spring.ms.laws.entities.TextoLey;
import com.kappacomputacion.spring.ms.laws.repositories.TextoLeyRepository;
import com.kappacomputacion.spring.ms.laws.shared.ServiceVariables;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date
 */
@Service
public class TextoLeyService extends ServiceVariables {
    private final TextoLeyRepository textoLeyRepository;

    private Map<String, String> countriesMap = new HashMap<>();

    @Autowired
    public TextoLeyService(TextoLeyRepository textoLeyRepository) {
        this.textoLeyRepository = textoLeyRepository;

        countriesMap.put("GT", "Guatemala");
        countriesMap.put("CR", "Costa Rica");
        countriesMap.put("HN", "Honduras");
        countriesMap.put("SV", "El Salvador");
        countriesMap.put("NI", "Nicaragua");
        countriesMap.put("PA", "Panama");
        countriesMap.put("CO", "Colombia");
        countriesMap.put("DO", "República Dominicana");
    }

    public TextoLey createTextoLeyEntity(TextoLeyDto textoLeyDto, LawsMain law) {
        TextoLey entity = new TextoLey();
        entity.setRecordId(textoLeyDto.getRecordId());
        entity.setItemId(law);
        entity.setIaItem(textoLeyDto.getIaItem());
        entity.setFuenteId(textoLeyDto.getFuenteId());
        entity.setFuenteArticuloNumero(textoLeyDto.getFuenteArticuloNumero());
        entity.setFuenteArticuloNombre(textoLeyDto.getFuenteArticuloNombre());
        entity.setFuenteArticuloTexto(textoLeyDto.getFuenteArticuloTexto());
        entity.setVersion(textoLeyDto.getVersion());

        return entity;
    }

    public LawNode createTextoLeyForTreeUi(List<TextoLeyDto> nodes) {

        LawNode lawNode = new LawNode();

        String leadLawKey = "";

        for (TextoLeyDto node : nodes) {
            LawContentChild lawContentChild = new LawContentChild();
            lawContentChild.setId(node.getRecordId());
            lawContentChild.setName(node.getFuenteArticuloNumero() + ' ' + node.getFuenteArticuloNombre());
            lawContentChild.setParent(node.getItemId());

            if (leadLawKey.isEmpty()) {
                leadLawKey = node.getItemId();

                lawNode.setId(node.getCountry());
                lawNode.setName(node.getFuenteId());
                lawNode.setParent(node.getItemId());
            }

            lawNode.getChildren().add(lawContentChild);
        }

        return lawNode;
    }

    public LawNode createTextoLeyForTreeUiWindows(List<TextoLeyTreeUiDto> nodes) {

        LawNode lawNode = new LawNode();

        String leadLawKey = "";

        for (TextoLeyTreeUiDto node : nodes) {
            LawContentChild lawContentChild = new LawContentChild();

            lawContentChild.setId(node.getRecordId());
            lawContentChild.setName(node.getFuenteArticuloNumero() + ' ' + node.getFuenteArticuloNombre());
            lawContentChild.setParent(node.getItemId());

            if (leadLawKey.isEmpty()) {
                leadLawKey = node.getItemId();

                lawNode.setId(node.getItemId());
                lawNode.setName(node.getLawName());
                lawNode.setParent("#");
            }

            lawNode.getChildren().add(lawContentChild);
        }

        return lawNode;
    }

    public TextoLeyDto getLawContent(String recordId) {
        Optional<TextoLey> textoLey = textoLeyRepository.findById(recordId);
        TextoLeyDto articleContent;
        selectError = "Contenido Artículo no existe!!";

        if (textoLey.isPresent()) {
            TextoLey content = textoLey.get();
            selectError = "Contenido Artículo recuperado con éxito!!";
            articleContent = new TextoLeyDto(
                    content.getRecordId(), content.getItemId().getItemId(), content.getIaItem(), content.getFuenteId(),
                    content.getFuenteArticuloNumero(), content.getFuenteArticuloNombre(),
                    content.getFuenteArticuloTexto(), content.getVersion(), ""
            );

            return articleContent;
        }

        return null;
    }

    public TextoLey getEntityLawContent(String recordId) {
        Optional<TextoLey> textoLey = textoLeyRepository.findById(recordId);
        TextoLeyDto articleContent;
        selectError = "Contenido Artículo no existe!!";

        if (textoLey.isPresent()) {
            TextoLey content = textoLey.get();
            selectError = "Contenido Artículo recuperado con éxito!!";

            return content;
        }

        return null;
    }

    public List<TextoLeyDto> getListOfContentLaws() {
        Sort sort = Sort.by(Sort.Order.asc("itemId"), Sort.Order.asc("fuenteArticuloNumero"));
        errorOccurred = false;
        selectError = "Leyes recuperadas con éxito!!";
        List<TextoLey> nodes = textoLeyRepository.findAll(sort);
        List<TextoLeyDto> nodesDto = new ArrayList<>();

        nodes.forEach(
                node -> {
                    TextoLeyDto newNode = new TextoLeyDto(
                            node.getRecordId(), node.getItemId().getItemId(), node.getIaItem(), node.getFuenteId(),
                            node.getFuenteArticuloNumero(), node.getFuenteArticuloNombre(),
                            node.getFuenteArticuloTexto(), node.getVersion(), node.getItemId().getCountry()
                    );
                    nodesDto.add(newNode);
                }
        );

        Comparator<TextoLeyDto> comparator = Comparator
                .comparing(TextoLeyDto::getCountry)
                .thenComparing(TextoLeyDto::getItemId)
                .thenComparing(
                        (dto1, dto2) -> {
                            int num1 = extractNumber(dto1.getFuenteArticuloNumero());
                            int num2 = extractNumber(dto2.getFuenteArticuloNumero());
                            return Integer.compare(num1, num2);
                        }
                );

        // Sort the list using the custom Comparator
        nodesDto.sort(comparator);

        return nodesDto;
    }

    public List<TextoLeyTreeUiDto> getLawContentByLawKey(@NotNull LawsMain lawMain) {
        List<TextoLey> lawContent = this.textoLeyRepository.findByItemIdOrderByFuenteArticuloNumero(lawMain);
        List<TextoLeyTreeUiDto> nodesDto = new ArrayList<>();

        if (!lawContent.isEmpty()) {
            lawContent.forEach(
                    node -> {
                        TextoLeyTreeUiDto newNode = new TextoLeyTreeUiDto(
                                node.getRecordId(), node.getItemId().getItemId(), node.getItemId().getDecretoNombre(),
                                node.getFuenteId(),
                                node.getFuenteArticuloNumero(), node.getFuenteArticuloNombre(),
                                node.getFuenteArticuloTexto(), node.getVersion(), node.getItemId().getCountry()
                        );
                        nodesDto.add(newNode);
                    }
            );
        }

        Comparator<TextoLeyTreeUiDto> comparator = Comparator
                .comparing(TextoLeyTreeUiDto::getCountry)
                .thenComparing(
                        (dto1, dto2) -> {
                            int num1 = extractNumber(dto1.getFuenteArticuloNumero());
                            int num2 = extractNumber(dto2.getFuenteArticuloNumero());
                            return Integer.compare(num1, num2);
                        }
                );

        // Sort the list using the custom Comparator
        nodesDto.sort(comparator);

        return nodesDto;
    }

    // Helper function to extract the numeric part from a string
    private static int extractNumber(String input) {
        String numericPart = input.replaceAll("\\D", ""); // Remove non-digits
        return numericPart.isEmpty() ? 0 : Integer.parseInt(numericPart);
    }

    public TextoLey getLawText(String recordId) {
        TextoLey entity = null;
        Optional<TextoLey> text = textoLeyRepository.findById(recordId);

        if (text.isPresent()) {
            entity = text.get();
        }

        return entity;
    }

    public TextoLey saveNewLawContent(TextoLey entity) {
        TextoLey newEntity = null;

        errorOccurred = false;

        try {
            insertError = "Texto de ley grabada con éxito!!";
            newEntity = textoLeyRepository.save(entity);
        } catch (Exception ex) {
            errorOccurred = true;
            insertError = ex.getMessage();
            System.out.println(ex.getMessage());
        }

        return newEntity;
    }

    public int updateLawContent(TextoLeyUpdateDto updatedContent, TextoLey managedEntity) {
        updateError = "";
        updated = 0;
        errorOccurred = false;
        int newVersion = updatedContent.getVersion();

        if (updatedContent.getVersion() == managedEntity.getVersion()) {
            if (updatedContent.isFuenteArticuloTextoChanged()) {
                managedEntity.setFuenteArticuloTexto(updatedContent.getFuenteArticuloTexto());
                updated++;
                updateError += "<br/> Descripción actualizada.";
            }

            if(updatedContent.isIaItemChanged()){
                managedEntity.setIaItem(updatedContent.getIaItem());
                updated++;
                updateError += "<br/> Relación con IA actualizada.";
            }

            if (updatedContent.isFuenteArticuloNumeroChanged()) {
                managedEntity.setFuenteArticuloNumero(updatedContent.getFuenteArticuloNumero());
                updated++;
                updateError += "<br/> Número artículo actualizado.";
            }

            if (updatedContent.isFuenteArticuloNombreChanged()) {
                managedEntity.setFuenteArticuloNombre(updatedContent.getFuenteArticuloNombre());
                updated++;
                updateError += "<br/> Nombre artículo actualizado.";
            }

            if (updatedContent.isFuenteIdChanged()) {
                managedEntity.setFuenteId(updatedContent.getFuenteId());
                updated++;
                updateError += "<br/> Número de ley actualizado.";
            }

            if (updated > 0) {
                managedEntity.setVersion(updatedContent.getVersion() + 1);
                this.textoLeyRepository.save(managedEntity);
                updateError += "<br/> Versión actualizada.";
                newVersion = newVersion + 1;
            }
        } else {
            errorOccurred = true;
            updateError = "La versión de ley enviada al servidor no coincide con la almacenada en la base de datos. Verifique si alguien hizo cambios.";
        }

        return newVersion;
    }

    public boolean deleteLawContent(TextoLey managedEntity) {
        deleteError = "";
        errorOccurred = false;
        boolean deleted = false;

        try {
            this.textoLeyRepository.delete(managedEntity);
            deleteError = managedEntity.getFuenteArticuloNombre() + " fué eliminado exitosamente!!";
            deleted = true;
        } catch (Exception ex) {
            errorOccurred = true;
            deleteError = ex.getMessage();
            System.out.println(ex.getMessage());
        }

        return deleted;
    }

}
