package com.kappacomputacion.spring.ms.laws.services;

import com.kappacomputacion.spring.ms.laws.dtos.LawsMainDto;
import com.kappacomputacion.spring.ms.laws.dtos.LawsMainUpdateDto;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.LawContentChild;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.LawNode;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.CountryNode;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.TreeProcessNodes;
import com.kappacomputacion.spring.ms.laws.entities.LawsMain;
import com.kappacomputacion.spring.ms.laws.repositories.LawsMainRepository;
import com.kappacomputacion.spring.ms.laws.shared.ServiceVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date
 */
@Service
public class LawsMainService extends ServiceVariables {
    private final LawsMainRepository repository;
    private Map<String, String> countriesMap = new HashMap<>();

    @Autowired
    public LawsMainService(LawsMainRepository repository) {
        this.repository = repository;
        countriesMap.put("GT", "Guatemala");
        countriesMap.put("CR", "Costa Rica");
        countriesMap.put("HN", "Honduras");
        countriesMap.put("SV", "El Salvador");
        countriesMap.put("NI", "Nicaragua");
        countriesMap.put("PA", "Panama");
        countriesMap.put("CO", "Colombia");
        countriesMap.put("DO", "República Dominicana");
    }

    public LawsMain createLawsMainEntity(LawsMainDto newLaw) {
        LawsMain entity = new LawsMain();
        entity.setItemId(newLaw.getItemId());
        entity.setIaItem(newLaw.getIaItem());
        entity.setDecretoId(newLaw.getDecretoId());
        entity.setCountry(newLaw.getCountry());
        entity.setLan(newLaw.getLan());
        entity.setDecretoNombre(newLaw.getDecretoNombre());
        entity.setDecretante(newLaw.getDecretante());
        entity.setDecretoDescripcion(newLaw.getDecretoDescripcion());
        entity.setInicioVigencia(newLaw.getInicioVigencia());
        entity.setFinVigencia(newLaw.getFinVigencia());
        entity.setFechaPublicacion(newLaw.getFechaPublicacion());
        entity.setTipoLey(newLaw.getTipoLey());
        entity.setVersion(newLaw.getVersion());
        return entity;
    }

    public LawsMain getLaw(String lawId) {
        LawsMain entity = null;
        Optional<LawsMain> law = repository.findById(lawId);
        errorOccurred = false;

        if (law.isPresent()) {
            entity = law.get();
            selectError = "Ley encontrada satisfactoriamente!!";
        } else {
            errorOccurred = true;
            selectError = "Ley no existe en  base de datos!!";
        }

        return entity;
    }



    public List<LawsMain> getLawsForTreeNodesUI() {
        Sort sort = Sort.by(Sort.Order.asc("country"), Sort.Order.asc("fechaPublicacion"));
        errorOccurred = false;
        selectError = "Leyes recuperadas con éxito!!";
        return repository.findAll(sort);
    }

    public LawsMain saveNewLaw(LawsMain entity) {
        LawsMain newEntity;
        errorOccurred = false;

        try {
            insertError = "Ley grabada con éxito!!";
            newEntity = repository.save(entity);
        } catch (Exception ex) {
            errorOccurred = true;
            insertError = ex.getMessage();
            System.out.println(ex.getMessage());
            newEntity = null;
        }

        return newEntity;
    }

    public int updateLawJpaStandard(LawsMainUpdateDto entityUpdate, LawsMain managedEntity) {
        updateError = "";
        updated = 0;
        int newVersion = entityUpdate.getVersion();

        if(entityUpdate.getVersion() == managedEntity.getVersion()) {
            if (entityUpdate.isDecretoIdChanged()) {
                managedEntity.setDecretoId(entityUpdate.getDecretoId());
                updated++;
                updateError += "<br/> Decreto actualizado.";
            }

            if(entityUpdate.isIaItemChanged()){
                managedEntity.setIaItem(entityUpdate.getIaItem());
                updated++;
                updateError += "Relación IA actualizada.";
            }

            if (entityUpdate.isCountryChanged()) {
                managedEntity.setCountry(entityUpdate.getCountry());
                updated++;
                updateError += "<br/> País actualizado.";
            }

            if (entityUpdate.isLanChanged()) {
                managedEntity.setLan(entityUpdate.getLan());
                updated++;
                updateError += "<br/> Idioma actualizado.";
            }

            if (entityUpdate.isDecretoNombreChanged()) {
                managedEntity.setDecretoNombre(entityUpdate.getDecretoNombre());
                updated++;
                updateError += "<br/> Nombre decreto actualizado.";

            }

            if (entityUpdate.isDecretoDescripcionChanged()) {
                managedEntity.setDecretoDescripcion(entityUpdate.getDecretoDescripcion());
                updated++;
                updateError += "<br/> Descripción actualizada.";

            }

            if (entityUpdate.isInicioVigenciaChanged()) {
                managedEntity.setInicioVigencia(entityUpdate.getInicioVigencia());
                updated++;
                updateError += "<br/> Inicio vigencia actualizada.";
            }

            if (entityUpdate.isFinVigenciaChanged()) {
                managedEntity.setFinVigencia(entityUpdate.getFinVigencia());
                updated++;
                updateError += "<br/> Fin vigencia actualizada.";

            }

            if (entityUpdate.isFechaPublicacionChanged()) {
                managedEntity.setFechaPublicacion(entityUpdate.getFechaPublicacion());
                updated++;
                updateError += "<br/> Fecha publicación actualizada.";

            }

            if (entityUpdate.isDecretanteChanged()) {
                managedEntity.setDecretante(entityUpdate.getDecretante());
                updated++;
                updateError += "<br/> Decretante actualizado.";

            }

            if (entityUpdate.isTipoLeyChanged()) {
                managedEntity.setTipoLey(entityUpdate.getTipoLey());
                updated++;
                updateError += "<br/> Tipo ley actualizada.";

            }

            if (updated > 0) {
                managedEntity.setVersion(entityUpdate.getVersion() + 1);
                this.repository.save(managedEntity);
                updateError += "<br/> Versión actualizada.";
                newVersion = newVersion + 1;
            }
        }else{
            errorOccurred = true;
            updateError = "La versión de ley enviada al servidor no coincide con la almacenada en la base de datos. Verifique si alguien hizo cambios.";
        }

        return newVersion;
    }

    public int updateLaw(LawsMainUpdateDto entityUpdate) {
        updated = 0;
        int newVersion = entityUpdate.getVersion();

        if (entityUpdate.isDecretoIdChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewDecretoId(entityUpdate.getItemId(), entityUpdate.getDecretoId(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "Decreto número actualizado.";
            }
        }

        if (entityUpdate.isCountryChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewCountry(entityUpdate.getItemId(), entityUpdate.getCountry(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> País actualizado.";
            }
        }

        if (entityUpdate.isLanChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewLan(entityUpdate.getItemId(), entityUpdate.getLan(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Idioma actualizado.";
            }
        }

        if (entityUpdate.isDecretoNombreChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewDecretoNombre(entityUpdate.getItemId(), entityUpdate.getDecretoNombre(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Nombre decreto actualizado.";
            }
        }

        if (entityUpdate.isDecretoDescripcionChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewDecretoDescripcion(entityUpdate.getItemId(), entityUpdate.getDecretoDescripcion(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Descripción actualizada.";
            }
        }

        if (entityUpdate.isInicioVigenciaChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewInicioVigencia(entityUpdate.getItemId(), entityUpdate.getInicioVigencia(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Inicio vigencia actualizada.";
            }
        }

        if (entityUpdate.isFinVigenciaChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewFinVigencia(entityUpdate.getItemId(), entityUpdate.getFinVigencia(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Fin vigencia actualizada.";
            }
        }

        if (entityUpdate.isFechaPublicacionChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewFechaPublicacion(entityUpdate.getItemId(), entityUpdate.getFechaPublicacion(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Fecha publicación actualizada.";
            }
        }

        if (entityUpdate.isDecretanteChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewDecretante(entityUpdate.getItemId(), entityUpdate.getDecretante(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Decretante actualizado.";
            }
        }

        if (entityUpdate.isTipoLeyChanged()) {
            int valueUpdated = repository.updateLawsMainWithNewTipoLey(entityUpdate.getItemId(), entityUpdate.getTipoLey(), entityUpdate.getVersion());
            if (valueUpdated > 0) {
                updated++;
                updateError += "<br/> Tipo ley actualizada.";
            }
        }

        if (updated > 0) {
            repository.updateLawsMainWithNewVersion(entityUpdate.getItemId(), entityUpdate.getVersion() + 1, entityUpdate.getVersion());
            updateError += "<br/> Versión actualizada.";
            newVersion = newVersion + 1;
        }

        return newVersion;
    }

    public boolean deleteLaw(String lawKey) {
        try {
            repository.deleteById(lawKey);
            deleteError = "Ley eliminada";
            return true;
        } catch (IllegalArgumentException ex) {
            deleteError = "Ley no pudo ser eliminada!!";
            return false;
        }
    }

    public TreeProcessNodes createEntityForTreeUiNodes(List<LawsMain> recordSet) {
        TreeProcessNodes treeProcessNodes = new TreeProcessNodes();
        CountryNode countryNode = new CountryNode();

        String leadCountry = "";
        String lawName = "";
        String countryName = "";
        boolean addParentAtTheEnd = true;

        if (!recordSet.isEmpty()) {
            for (LawsMain node : recordSet) {

                LawNode lawNode = new LawNode();

                lawName = node.getDecretoId() + " " + node.getDecretoNombre();

                lawNode.setId(node.getItemId());
                lawNode.setName(lawName);
                lawNode.setParent(node.getCountry());

                // si es "" crear parent
                if (!leadCountry.equals(node.getCountry())) {
                    if (!leadCountry.isEmpty()) {
                        treeProcessNodes.getNodes().add(countryNode);
                    }

                    countryName = countriesMap.get(node.getCountry());
                    countryNode = new CountryNode();
                    countryNode.setName(node.getCountry()+" - "+countryName);
                    countryNode.setId(node.getCountry());
                    countryNode.setParent("#");

                    countryNode.getChildren().add(lawNode);
                    leadCountry = node.getCountry();
                } else {
                    countryNode.getChildren().add(lawNode);
                }
            }
            treeProcessNodes.getNodes().add(countryNode);
        }

        return treeProcessNodes;
    }

}
