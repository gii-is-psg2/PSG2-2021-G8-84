<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="vets">
	<h2>Veterinarios</h2>
    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Especialidades</th>
            <sec:authorize access="hasAuthority('admin')">
            <th>Opciones</th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">ninguna</c:if>
                </td>
                <sec:authorize access="hasAuthority('admin')">
                <td>
                	<spring:url value="/vets/{vetId}/edit" var="vetUrl"> <spring:param name="vetId" value="${vet.id}" />
					          </spring:url> <a href="${fn:escapeXml(vetUrl)}" class="btn btn-default">Editar veterinario</a>
                  	<spring:url value="/vets/{vetId}/delete" var="vetUrl2"> <spring:param name="vetId" value="${vet.id}" />
					          </spring:url> <a href="${fn:escapeXml(vetUrl2)}" class="btn btn-default">Eliminar veterinario</a>
				</td>
				</sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <table class="table-buttons">
    	<sec:authorize access="hasAuthority('admin')">
        <tr>   
            <td>
                <a href="<spring:url value="/vets/new" htmlEscape="true" />" class="btn btn-default">Añadir nuevo veterinario</a>
            </td>        
        </tr>
        </sec:authorize>
    </table>
</petclinic:layout>
