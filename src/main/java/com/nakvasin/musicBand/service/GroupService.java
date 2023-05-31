package com.nakvasin.musicBand.service;

import com.nakvasin.musicBand.dto.GroupDTO;
import com.nakvasin.musicBand.entity.Group;
import com.nakvasin.musicBand.entity.Member;
import com.nakvasin.musicBand.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Mono<ServerResponse> getGroups(ServerRequest serverRequest) {
        Flux<Group> groups = groupRepository.findAll()
                .publishOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new ChangeSetPersister.NotFoundException()));

        return ServerResponse.ok().contentType(APPLICATION_JSON).body(groups, Group.class);
    }

    public Mono<ServerResponse> getYearAndCountry(ServerRequest serverRequest) {
        String groupName = serverRequest.pathVariable("groupName");
        Mono<GroupDTO> yearAndCountry = groupRepository.getGroupByName(groupName)
                .publishOn(Schedulers.boundedElastic())
                .filter(e -> e.getName().equals(groupName))
                .flatMap(group -> {
                    if (group != null) {
                        return Mono.just(GroupDTO.builder()
                                .name(group.getName())
                                .year(group.getYear())
                                .country(group.getCountry())
                                .build());
                    } else {
                        return Mono.error(new ChangeSetPersister.NotFoundException());
                    }
                });

        return ServerResponse.ok().contentType(APPLICATION_JSON).body(yearAndCountry, GroupDTO.class);
    }

    public Mono<ServerResponse> getGroupMembers(ServerRequest serverRequest) {
        String groupId = serverRequest.pathVariable("groupId");
        Mono<List<Member>> members = groupRepository.findById(groupId)
                .map(Group::getMembers)
                .switchIfEmpty(Mono.error(new ChangeSetPersister.NotFoundException()))
                .flatMapIterable(member -> member)
                .collectList();

        return ServerResponse.ok().contentType(APPLICATION_JSON).body(members, new ParameterizedTypeReference<>() {
        });
    }

    public Mono<ServerResponse> createGroup(ServerRequest request) {
        Mono<Group> groupMono = request.bodyToMono(Group.class);

        return groupMono.flatMap(group -> groupRepository.findById(group.getId())
                .flatMap(existingGroup -> Mono.error(new IllegalStateException("Группа с ID " + group.getId() + " уже существует")))
                .switchIfEmpty(Mono.defer(() -> {
                    Group newGroup = Group.builder()
                            .name(group.getName())
                            .year(group.getYear())
                            .country(group.getCountry())
                            .members(group.getMembers())
                            .chartPosition(group.getChartPosition())
                            .build();

                    return groupRepository.save(newGroup);
                }))
                .flatMap(savedGroup -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(savedGroup))
                .onErrorResume(IllegalArgumentException.class, ex -> ServerResponse.badRequest().bodyValue("Неверный запрос"))
                .onErrorResume(IllegalStateException.class, ex -> ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(RuntimeException.class, ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue("Ошибка. Не могу создать группу")));
    }

    public Mono<ServerResponse> updateChartPosition(ServerRequest serverRequest) {
        String groupName = serverRequest.pathVariable("groupName");
        int newChartPosition = Integer.parseInt(serverRequest.pathVariable("position"));

        if (groupName.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Группа не может быть пустой"));
        }

        if (newChartPosition < 1) {
            return Mono.error(new IllegalArgumentException("Позиция в чарте не может быть меньше, чем 1"));
        }

        return groupRepository.getGroupByName(groupName)
                .map(group -> Group.builder()
                        .id(group.getId())
                        .name(group.getName())
                        .year(group.getYear())
                        .country(group.getCountry())
                        .members(group.getMembers())
                        .chartPosition(newChartPosition)
                        .build())
                .flatMap(groupRepository::save)
                .flatMap(updatedGroup -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedGroup))
                .switchIfEmpty(Mono.error(new IllegalStateException("Группа с именем " + groupName + " не найдена")));
    }

    public Mono<ServerResponse> deleteGroupMember(ServerRequest serverRequest) {
        String groupName = serverRequest.pathVariable("groupName");
        String groupMember = serverRequest.pathVariable("groupMember");

        if (groupName.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Группа не может быть пустой"));
        }

        if (groupMember.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Поле члена группы не может быть пустым"));
        }

        return groupRepository.getGroupByName(groupName)
                .map(group -> {
                    List<Member> membersList = group.getMembers().stream()
                            .filter(member -> !member.getName().equals(groupMember))
                            .toList();

                    return Group.builder()
                            .id(group.getId())
                            .name(group.getName())
                            .year(group.getYear())
                            .country(group.getCountry())
                            .members(membersList)
                            .chartPosition(group.getChartPosition())
                            .build();
                })
                .flatMap(groupRepository::save)
                .flatMap(updatedGroup -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedGroup))
                .switchIfEmpty(Mono.error(new IllegalStateException("Группа с именем " + groupName + " не найдена")));
    }
}
