package com.example.clientcomputer.service;

import javax.servlet.http.HttpSession;

import com.example.clientcomputer.model.Import;

public interface ImportService {

	void save(Import importObject, HttpSession session);

}
