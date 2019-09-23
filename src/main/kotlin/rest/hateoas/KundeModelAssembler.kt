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
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.reactive.SimpleReactiveRepresentationModelAssembler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

/**
 * Mit der Klasse [KundeModelAssembler] können Entity-Objekte der Klasse [de.hska.kunde.entity.Kunde].
 * in eine HATEOAS-Repräsentation transformiert werden.
 *
 * @author [Jürgen Zimmermann](mailto:Juergen.Zimmermann@HS-Karlsruhe.de)
 *
 * @constructor Ein KundeModelAssembler erzeugen.
 */
@Component
class KundeModelAssembler : SimpleReactiveRepresentationModelAssembler<Kunde> {
    /**
     * EntityModel eines Kunde-Objektes (gemäß Spring HATEOAS) um Atom-Links ergänzen.
     * @param kundeModel Gefundenes Kunde-Objekt als EntityModel gemäß Spring HATEOAS
     * @param exchange Kapselt den eingegangenen Request mit insbesondere der aufgerufenen URI
     * @return Model für den Kunden mit Atom-Links für HATEOAS
     */
    override fun addLinks(kundeModel: EntityModel<Kunde>, exchange: ServerWebExchange): EntityModel<Kunde> {
        val uri = exchange.request.uri.toString()
        val id = kundeModel.content?.id

        val baseUri = uri.substringBefore('?')
            .removeSuffix("/")
            .removeSuffix("/$id")
        val idUri = "$baseUri/$id"

        val selfLink = Link(idUri)
        val listLink = Link(baseUri, "list")
        val addLink = Link(baseUri, "add")
        val updateLink = Link(idUri, "update")
        val removeLink = Link(idUri, "remove")
        return kundeModel.add(selfLink, listLink, addLink, updateLink, removeLink)
    }
}
