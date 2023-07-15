package com.project.fmproject.domain.specification;

import com.project.fmproject.domain.model.Equipamentos;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EquipamentosSpecification {

    public static Specification<Equipamentos> filtrarEquipamentos(String anoCadastro, String tagEquipamento, String norma,
                                                                  String inspecaoExterna, String inspecaoInterna,
                                                                  String proximaInspecaoExterna,
                                                                  String proximaInspecaoInterna,
                                                                  String dataCalibracao, String proximaCalibracao,
                                                                  Long idEmpresa) {
        return (Root<Equipamentos> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (anoCadastro != null) {
                predicates.add(criteriaBuilder.equal(root.get("anoCadastro"), anoCadastro));
            }

            if (tagEquipamento != null) {
                predicates.add(criteriaBuilder.equal(root.get("tagEquipamento"), tagEquipamento));
            }

            if (norma != null) {
                predicates.add(criteriaBuilder.equal(root.get("norma"), norma));
            }

            if (inspecaoExterna != null) {
                predicates.add(criteriaBuilder.equal(root.get("inspecaoExterna"), inspecaoExterna));
            }

            if (inspecaoInterna != null) {
                predicates.add(criteriaBuilder.equal(root.get("inspecaoInterna"), inspecaoInterna));
            }

            if (proximaInspecaoExterna != null) {
                predicates.add(criteriaBuilder.equal(root.get("proximaInspecaoExterna"), proximaInspecaoExterna));
            }

            if (proximaInspecaoInterna != null) {
                predicates.add(criteriaBuilder.equal(root.get("proximaInspecaoInterna"), proximaInspecaoInterna));
            }

            if (dataCalibracao != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataCalibracao"), dataCalibracao));
            }

            if (proximaCalibracao != null) {
                predicates.add(criteriaBuilder.equal(root.get("proximaCalibracao"), proximaCalibracao));
            }

            if (idEmpresa != null) {
                predicates.add(criteriaBuilder.equal(root.get("empresa").get("id"), idEmpresa));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
