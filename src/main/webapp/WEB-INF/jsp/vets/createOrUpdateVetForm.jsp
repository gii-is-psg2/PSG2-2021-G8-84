<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    <jsp:body>
        <h2>
            <c:if test="${vet['new']}">New </c:if> Vet
        </h2>
        <form:form modelAttribute="vet" class="form-horizontal">
            <input type="hidden" name="id" value="${vet.id}"/>
            <div class="form-group has-feedback">
                <petclinic:inputField label="First Name" name="firstName"/>
                <petclinic:inputField label="Last Name" name="lastName"/>
                <petclinic:selectFieldN label="Specialties" name="specialties" names="${specs}"></petclinic:selectFieldN>
                <div style=text-align:right>Specialties: 1=radiology, 2=surgery, 3=dentistry</div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${vet['new']}">
                            <button class="btn btn-default" type="submit">Add vet</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Edit vet</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!vet['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>