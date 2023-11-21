package com.project.fmproject.domain.specification;

import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.EquipamentosRepository;
import com.project.fmproject.domain.service.enums.TipoEquipamentoEnum;

import org.springframework.beans.factory.annotation.Autowired;
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
                                                                  String inspecao, String proximaInspecao,
                                                                  Long idEmpresa, TipoEquipamentoEnum tipoEquipamento, String empresa, String anoVencimento) {
        return (Root<Equipamentos> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (anoCadastro != null) {
                predicates.add(criteriaBuilder.like(root.get("anoCadastro"), "%" + anoCadastro + "%"));
            }

            if (anoVencimento != null) {
                List<Predicate> orPredicates = new ArrayList<>();

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaInspecaoExterna")),
                    anoVencimento
                ));

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaInspecaoInterna")),
                    anoVencimento
                ));

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaCalibracao")),
                    anoVencimento
                ));

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaInspecao")),
                    anoVencimento
                ));
            
               predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
            }

            if (tagEquipamento != null) {
                predicates.add(criteriaBuilder.like(root.get("tagEquipamento"), "%" + tagEquipamento + "%"));
            }

            if (norma != null) {
                predicates.add(criteriaBuilder.like(root.get("norma"), "%" + norma + "%"));
            }

            if (inspecaoExterna != null) {
                predicates.add(criteriaBuilder.like(root.get("inspecaoExterna"), "%" + inspecaoExterna + "%"));
            }

            if (inspecaoInterna != null) {
                predicates.add(criteriaBuilder.like(root.get("inspecaoInterna"), "%" + inspecaoInterna + "%"));
            }

            if (proximaInspecaoExterna != null) {
                predicates.add(criteriaBuilder.like(root.get("proximaInspecaoExterna"), "%" + proximaInspecaoExterna + "%"));
            }

            if (proximaInspecaoInterna != null) {
                predicates.add(criteriaBuilder.like(root.get("proximaInspecaoInterna"), "%" + proximaInspecaoInterna + "%"));
            }

            if (dataCalibracao != null) {
                predicates.add(criteriaBuilder.like(root.get("dataCalibracao"), "%" + dataCalibracao + "%"));
            }

            if (proximaCalibracao != null) {
                predicates.add(criteriaBuilder.like(root.get("proximaCalibracao"), "%" + proximaCalibracao + "%"));
            }

            if (inspecao != null) {
                predicates.add(criteriaBuilder.like(root.get("inspecao"), "%" + inspecao + "%"));
            }

            if (proximaInspecao != null) {
                predicates.add(criteriaBuilder.like(root.get("proximaInspecao"), "%" + proximaInspecao + "%"));
            }

            if (idEmpresa != null) {
                predicates.add(criteriaBuilder.equal(root.get("empresa").get("id"), idEmpresa));
            }

            if (tipoEquipamento != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoEquipamento"), tipoEquipamento));
            }
           
            if (empresa != null) {
                predicates.add(criteriaBuilder.like(root.get("empresa").get("nome"), "%" + empresa + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<Equipamentos> filtrarEquipamentosPorVencimento(String anoVencimento ,
                                                                  Long idEmpresa ) {
        return (Root<Equipamentos> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (anoVencimento != null) {
                List<Predicate> orPredicates = new ArrayList<>();

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaInspecaoExterna")),
                    anoVencimento
                ));

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaInspecaoInterna")),
                    anoVencimento
                ));

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaCalibracao")),
                    anoVencimento
                ));

                orPredicates.add(criteriaBuilder.equal(
                    criteriaBuilder.function("YEAR", Integer.class, root.get("proximaInspecao")),
                    anoVencimento
                ));
            
               predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
            }

            if (idEmpresa != null) {
                predicates.add(criteriaBuilder.equal(root.get("empresa").get("id"), idEmpresa));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

