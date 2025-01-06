package com.kappacomputacion.spring.ms.laws.repositories;

import com.kappacomputacion.spring.ms.laws.entities.LawsMain;
import com.kappacomputacion.spring.ms.laws.entities.treeui.MainLawTreeUiNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LawsMainRepository extends JpaRepository<LawsMain, String> {

    List<LawsMain> findByCountryOrderByFechaPublicacion(String country);


    @Query(value = "select law.item_id, law.decreto_id, law.decreto_nombre, law.country, pais.country_name from laws.laws_main as law " +
            "left join new_global.planet_countries as pais on law.country = pais.isoCountryCode " +
            "where pais.lan= :lan " +
            "order by law.country, law.fecha_publicacion",
            nativeQuery = true)
    Optional<List<MainLawTreeUiNode>> getTreeUiNodes(@Param("lan") String lan);

    @Modifying
    @Query("UPDATE LawsMain u SET u.decretoId = :newDecretoId WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewDecretoId(@Param("itemId") String itemId, @Param("newDecretoId") String newDecretoId, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.country = :newCountry WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewCountry(@Param("itemId") String itemId, @Param("newCountry") String newCountry, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.lan = :newLan WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewLan(@Param("itemId") String itemId, @Param("newLan") String newLan, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.decretoNombre = :newDecretoNombre WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewDecretoNombre(@Param("itemId") String itemId, @Param("newDecretoNombre") String newDecretoNombre, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.decretoDescripcion = :DecretoDescripcion WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewDecretoDescripcion(@Param("itemId") String itemId, @Param("DecretoDescripcion") String DecretoDescripcion, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.inicioVigencia = :inicioVigencia WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewInicioVigencia(@Param("itemId") String itemId, @Param("inicioVigencia") LocalDate inicioVigencia, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.fechaPublicacion = :fechaPublicacion WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewFechaPublicacion(@Param("itemId") String itemId, @Param("fechaPublicacion") LocalDate fechaPublicacion, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.finVigencia = :finVigencia WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewFinVigencia(@Param("itemId") String itemId, @Param("finVigencia") LocalDate finVigencia, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.decretante = :decretante WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewDecretante(@Param("itemId") String itemId, @Param("decretante") String decretante, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.tipoLey = :tipoLey WHERE u.itemId = :itemId AND u.version = :version")
    int updateLawsMainWithNewTipoLey(@Param("itemId") String itemId, @Param("tipoLey") String tipoLey, @Param("version") int version);

    @Modifying
    @Query("UPDATE LawsMain u SET u.version = :newVersion WHERE u.itemId = :itemId AND u.version = :version")
    void updateLawsMainWithNewVersion(@Param("itemId") String itemId, @Param("newVersion") int newVersion, @Param("version") int version);

}