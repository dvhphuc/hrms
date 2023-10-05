//package com.hrms.usermanagement.dto;
//
//import com.hrms.usermanagement.controller.UserController;
//import com.hrms.usermanagement.model.User;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.BeanUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.RepresentationModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserDtoAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {
//
//    @Override
//    public EntityModel<UserDto> toModel(UserDto entity) {
//        return EntityModel.of(UserDto,
//                Link.valueOf(E))
//    }
//
//    @Override
//    public CollectionModel<EntityModel<UserDto>> toCollectionModel(Iterable<? extends UserDto> entities) {
//        return RepresentationModelAssembler.super.toCollectionModel(entities);
//    }
//}
