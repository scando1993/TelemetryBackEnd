/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.application.Formato;

/**
 *
 * @author leiber567
 */
public interface Relation {
    public Formato updateRelationFormato(Long id, Formato formato);
}
