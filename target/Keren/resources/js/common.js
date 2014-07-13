var $form,
	$topFields,
	$name,
	$button,
	$msg;

$(document).ready(function() {
	loadClasses();
});

function loadClasses() {
	  var table = $(".mainTable > tbody > tr:odd").addClass("odd");
}

function initVariables($pForm, $pTopFields, $pName, $pButton, $pMsg) {
	$form = $pForm;
	$topFields = $pTopFields;
	$name = $pName;
	$button = $pButton;
	$msg = $pMsg;
}

function focusOutNumberValidate() {
	// validate number fields when they lose focus
	$topFields.focusout(function() {
		checkTopNumFields($(this), $msg);
	});
}

function topFormValidationBeforeSubmit() {
	// Adding validation checks on top number and name fields before submit
	$form.submit(function(event) {
		// filter amount fields, with the condition that they are not empty
		// spaces
		var $topFieldsFiltered = 
			$topFields.filter(function() { 
								return $.trim($(this).val()) != ""; 
							});
		
		// unless validation checks on param fields pass, cancel the submission
		// of the form
		if (!checkTopNumFields($topFieldsFiltered, $msg, $button) || !checkNameField($name, $msg)) {
			event.preventDefault();
		}
	});
}

function checkTopNumFields() {
	var allValid = true;
	
	// for each of the top number fields
	$topFields.each(function() {
		var currFieldValid = true,
			$this = $(this),
			value = $this.val().trim();
		
		// if the value is not empty - check it
		if (value) {
			// if the current value isn't valid - stop further checks
			allValid = allValid && innerCheckNumField($this, $msg);
			return allValid;
		}
	});
	
	// disable the add\update button if needed
	disableOrderButton($button, allValid);
	
	// if everything is alright, hide error message
	if (allValid && $msg.is(":visible")) {
		$msg.hide();
	}
	
	return allValid;
}

function innerCheckNumField($pField, $pMsg) {
	var isNumeric = true,
		value = $pField.val();

	// if the value is not numeric
	if (!$.isNumeric(value)) {
		isNumeric = false;
		// show error message
		$pMsg.text('Please enter numbers only');
		$pMsg.show();
	} 
	
	return isNumeric;
}

function checkNameField() {
	// if there was no product name entered
	if (!$name.val().trim()) {
		// show error message
		$msg.text('Please enter a name');
		$msg.show();
		return false;
	}
	// otherwise - everything is alright, hide error message
	else {
		$msg.hide();
		return true;
	}	
}

// disable\enable submit button according to the parameter
function disableOrderButton($pButton, allValid) {
	$pButton.attr("disabled", !allValid);
}