package com.github.alechenninger.teamwork.repositories;

import com.github.alechenninger.teamwork.UserName;

import java.util.List;

public interface UserRepository {
  List<UserName> getUsers();
}
