/**
 * Copyright (c) 2002-2013 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
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
package org.neo4j.cypher.docgen.refcard

import org.neo4j.cypher.{ ExecutionResult, StatisticsChecker }
import org.neo4j.cypher.docgen.RefcardTest

class IndexTest extends RefcardTest with StatisticsChecker {
  val graphDescription = List("A:Person KNOWS B:Person")
  val title = "Index"
  val css = "write c4-4 c5-4 c6-3"

  override def assert(name: String, result: ExecutionResult) {
    name match {
      case "create-index" =>
        //        assertStats(result, indexAdded = 1)
        assert(result.toList.size === 0)
      case "drop-index" =>
        //        assertStats(result, indexDeleted = 1)
        assert(result.toList.size === 0)
      case "match" =>
        assertStats(result, nodesCreated = 0)
        assert(result.toList.size === 1)
    }
  }

  override def parameters(name: String): Map[String, Any] =
    name match {
      case "aname" =>
        Map("value" -> "Alice")
      case _ =>
        Map()
    }

  def text = """
###assertion=create-index
//

CREATE INDEX ON :Person(name)
###

Create an index on the label `Person` and property `name`.

###assertion=drop-index
//

DROP INDEX ON :Person(name)
###

Drop the index on the label `Person` and property `name`.

###assertion=match parameters=aname
//

MATCH (n:Person) WHERE n.name = {value}
###

Index is used automatically for equality comparison of properties on indexed label.
Only one index is used per query.
"""
}
