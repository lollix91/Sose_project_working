/*
 * generated by Xtext 2.12.0
 */
package org.xtext.example.mydsl.web

import com.google.inject.Guice
import com.google.inject.Injector
import org.eclipse.xtext.util.Modules2
import org.xtext.example.mydsl.MyAtlRuntimeModule
import org.xtext.example.mydsl.MyAtlStandaloneSetup
import org.xtext.example.mydsl.ide.MyAtlIdeModule

/**
 * Initialization support for running Xtext languages in web applications.
 */
class MyAtlWebSetup extends MyAtlStandaloneSetup {
	
	override Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new MyAtlRuntimeModule, new MyAtlIdeModule, new MyAtlWebModule))
	}
	
}
