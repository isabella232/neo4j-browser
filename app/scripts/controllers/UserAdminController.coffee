###!
Copyright (c) 2002-2016 "Neo Technology,"
Network Engine for Objects in Lund AB [http://neotechnology.com]

This file is part of Neo4j.

Neo4j is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
###

'use strict'

angular.module('neo4jApp.controllers')
  .controller 'UserAdminController', [
    '$scope', 'Settings', 'ProtocolFactory', '$timeout'
  ($scope, Settings, ProtocolFactory, $timeout) ->
    $scope.autoRefresh = false

    $scope.refresh = () ->
      ProtocolFactory.getStoredProcedureService().getUserList().then((response) ->
        $scope.users = response.filter((user) -> $scope.user.username isnt user.username)
        ).catch((r) -> )
    $scope.activate = (username) ->
      ProtocolFactory.getStoredProcedureService().activateUser(username).then(() ->
        $scope.refresh()
      ).catch((r) -> )
    $scope.suspend = (username) ->
      ProtocolFactory.getStoredProcedureService().suspendUser(username).then(() ->
        $scope.refresh()
      ).catch((r) -> )
    $scope.delete = (username) ->
      ProtocolFactory.getStoredProcedureService().deleteUser(username).then(() ->
        debugger
        $scope.refresh()
      ).catch((r) -> )

    $scope.refresh()
]
