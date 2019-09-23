/*
 * Copyright (C) 2019 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.hska.kunde.rest.hateoas

import de.hska.kunde.entity.Kunde
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * Mit der Klasse [ListKundeModelAssembler] können Entity-Objekte der Klasse [de.hska.kunde.entity.Kunde].
 * in eine HATEOAS-Repräsentation innerhalb einer Liste bzw. eines JSON-Arrays transformiert werden.
 *
 * @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@HS-Karlsruhe.de)
 */
@Component
class ListKundeModelAssembler : SimpleRepresentationModelAssembler<Kunde> {
    /**
     * Konvertierung eines (gefundenen) Kunde-Objektes in ein Model gemäß Spring HATEOAS .
     * @param kunde Gefundenes Kunde-Objekt oder null
     * @param request Der eingegangene Request mit insbesondere der aufgerufenen URI
     * @return Model für den Kunden mit Atom-Links für HATEOAS
     */
    fun toModel(kunde: Kunde, request: ServerRequest): EntityModel<Kunde> {
        val uri = request.uri().toString()
        val baseUri = uri.substringBefore('?').removeSuffix("/")
        val idUri = "$baseUri/${kunde.id}"

        val selfLink = Link(idUri)
        return toModel(kunde).add(selfLink)
    }

    override fun addLinks(model: EntityModel<Kunde>) = Unit

    override fun addLinks(model: CollectionModel<EntityModel<Kunde>>) = Unit
}
