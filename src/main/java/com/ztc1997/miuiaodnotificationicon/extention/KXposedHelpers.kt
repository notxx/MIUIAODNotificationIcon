package com.ztc1997.miuiaodnotificationicon.extention

import kotlin.reflect.KClass

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

object KXposedHelpers {
	fun findAndHookMethod(
		clazz: Class<*>, methodName: String, vararg parameterTypes: Class<*>,
		callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		XposedHelpers.findAndHookMethod(clazz, methodName, *parameterTypes, methodHookCallback(callback))

	fun findAndHookMethod(
		clazz: Class<*>, methodName: String,
		callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		findAndHookMethod(clazz = clazz, methodName = methodName, parameterTypes = *(arrayOf<Class<*>>()), callback = callback)

	fun findAndHookMethod(
		clazz: Class<*>, methodName: String, vararg parameterTypes: KClass<*>,
		callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		findAndHookMethod(clazz = clazz, methodName = methodName, parameterTypes = *(parameterTypes.map{ it.java }.toTypedArray()), callback = callback)

	fun findAndHookMethod(
	    clazz: KClass<*>, methodName: String, vararg parameterTypes: KClass<*>,
	    callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		findAndHookMethod(clazz = clazz.java, methodName = methodName, parameterTypes = *(parameterTypes.map{ it.java }.toTypedArray()), callback = callback)

	fun findAndHookMethod(
		className: String, classLoader: ClassLoader, methodName: String,
		vararg parameterTypes: Class<*>, callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? = XposedHelpers.findAndHookMethod(
		className,
		classLoader,
		methodName,
		*parameterTypes,
		methodHookCallback(callback)
	)

	fun findAndHookMethod(
		className: String, classLoader: ClassLoader, methodName: String,
		vararg parameterTypes: KClass<*>, callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? = findAndHookMethod(
		className = className,
		classLoader = classLoader,
		methodName = methodName,
		parameterTypes = *(parameterTypes.map{ it.java }.toTypedArray()),
		callback = callback
	)

	fun findAndHookConstructor(
		clazz: Class<*>, vararg parameterTypes: Class<*>,
		callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		XposedHelpers.findAndHookConstructor(clazz, *parameterTypes, methodHookCallback(callback))

	fun findAndHookConstructor(
		clazz: Class<*>, vararg parameterTypes: KClass<*>,
		callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		findAndHookConstructor(clazz = clazz, parameterTypes = *(parameterTypes.map{ it.java }.toTypedArray()), callback = callback)

	fun findAndHookConstructor(
		className: String, classLoader: ClassLoader,
		vararg parameterTypes: Class<*>, callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		XposedHelpers.findAndHookConstructor(className, classLoader, *parameterTypes, methodHookCallback(callback))

	fun findAndHookConstructor(
		className: String, classLoader: ClassLoader,
		vararg parameterTypes: KClass<*>, callback: _XC_MethodHook.() -> Unit
	): XC_MethodHook.Unhook? =
		findAndHookConstructor(className = className, classLoader = classLoader, parameterTypes = *(parameterTypes.map{ it.java }.toTypedArray()), callback = callback)

}