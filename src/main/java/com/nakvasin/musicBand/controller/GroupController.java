package com.nakvasin.musicBand.controller;

import com.nakvasin.musicBand.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    public RouterFunction<?> groupRoutes() {
        return route(GET("/groups").and(accept(MediaType.APPLICATION_JSON)), groupService::getGroups)
                .andRoute(GET("/groups/getYearAndCountry/{groupName}").and(accept(MediaType.APPLICATION_JSON)), groupService::getYearAndCountry)
                .andRoute(GET("/groups/getGroupMembers/{groupId}").and(accept(MediaType.APPLICATION_JSON)), groupService::getGroupMembers)
                .andRoute(POST("/groups/newGroup/"), groupService::createGroup)
                .andRoute(POST("/groups/{groupName}/chart/{position}"), groupService::updateChartPosition)
                .andRoute(DELETE("/groups/{groupName}/deleteMember/{groupMember}"), groupService::deleteGroupMember);
    }
}
