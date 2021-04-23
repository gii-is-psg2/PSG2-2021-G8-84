<%@page import="java.time.LocalDate"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<petclinic:layout pageName="Causes">
	<jsp:body>
        
        <h3>Nueva causa: </h3></br></br>
        
        <form:form modelAttribute="cause" class="form-horizontal">
			 <input type="hidden" name="ownerId" value="${owner.id}" />
			 <input type="hidden" name="gathered" value="0.0" />
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="name" />
				<petclinic:inputField label="Descripcion" name="description" />
				<petclinic:inputField label="Objetivo" name="target" />
				<petclinic:inputField label="ONG" name="ngo" />
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                         
                    <button class="btn btn-default" type="submit">Confirmar</button>
                </div>
            </div>
        </form:form>
        
        
    
    </jsp:body>

</petclinic:layout>