/**
 * FormValidation Plugin for jQuery 1.5
 * @author Luiz Alfredo Galiza
 * @verison 2.0.1
 */
(function($){	
	
	/**
	 * Configurações padrão utilizadas no Plugin
	 */
	$.validationSettings =  {
		//Seletor jQuery que será utlizado para identificar os validadores
		selector:		".validator",
		
		//Executa a verificação no evento onBlur do campo do formulário
		verifyOnBlur:	true,
		
		//Executa a verificação dos campos no momento do envio do formulário
		verifyOnSubmit:	true,
		
		//Estilo aplicado ao validador quando este é verificado positivamente
		cssOnValidated:	{
			display: "none"
		},
		
		//Estilo aplicado ao validador quando este é verificado negativamente
		cssOnFail:		{
			display:  "block",
			color:    "red",
			fontSize: "x-small"
		},
		
		//Callback acionado no momento em que o validador é acionado positivamente.
		//O parâmetro validator é o objeto jQuery referente ao validador em questão. 
		onValidated:	function(validator){
			return;
		},
		
		//Callback acionado no momento em que o validador é acionado negativamente.
		//O parâmetro validator é o objeto jQuery referente ao validador em questão. 	
		onFail:			function(validator){
			return;
		},
		
		//calback acionado quando todos os validadores são verificados
		//positivamente. A função deverá retornar true para que os dados sejam
		//enviados. O parâmetro form se refere ao objeto jQuery do formulário.
		onSubmit:		function(form){
			return true;
		}
	};
	
	/**
	 * Altera as configurações de validação
	 * @param options Object Configurações que serão mescladas com o padrão.
	 * @return Object As configurações atuais modificadas.
	 */
	$.validationOptions = function(options){
		$.validationSettings = $.extend($.validationSettings, options);
		return $.validationSettings;
	};
	
	/**
	 * Função que retorna todos os validadores de um campo específico.
	 * Caso haja mais de um campo especificado, serão retornados
	 * somente os validadores do primeiro campo.
	 * @param options Object Query dos validadores( formato: {selector: ".selector"} )
	 * @returns jQuery
	 */
	$.fn.validators = function(){
		var settings = $.validationSettings;
		return $(settings.selector+
				"[data-element="+$(this).attr("id")+"]");
	};
	
	/**
	 * Ativador da validação dos campos.
	 * Os campos somente serão avaliados em dois momentos: em onBlur, quando
	 * o campo perde o foco (padrão) e em onSubmit, antes do envio do formulário.
	 * Se a função onSubmit retornar true, então o formulário é enviado.
	 * @param options Object Configurações do plugin
	 * @returns jQuery
	 */	
	$.fn.formValidation = function(options){
		
		settings = $.validationOptions(options);
		
		this.each(function(){
			
			var validators = $(settings.selector, this);
			
			if (settings.verifyOnBlur){
				$("input,textarea,select", this).blur(function(){
					$(this).validators().verify();
				});
			}			
			
			$(this).submit(function(event){		
				
				var verified = true;
				
				if (settings.verifyOnSubmit){
					verified = validators.verify();
				}
				return verified && settings.onSubmit(this);
				
			});		
		});
		
		return this;
	};
	
	/**
	 * Verificador de validador.
	 * Esta função observa se os validadores especificados são atendidos.
	 * Se algum validador não for atendido, ele será mostrado pelo método .show()
	 * Em caso de verificação positiva em todos, é retornado true,
	 * caso contrário, false.
	 * @returns boolean true, em caso de sucesso, ou false, caso contrário.
	 */
	$.fn.verify = function(){
		
		var success = true;
		var settings = $.validationSettings;
		
		this.each(function(){
			
			var type = $(this).attr("data-type");
			var element = $(this).attr("data-element");		
			var max = parseFloat($(this).attr("data-max"));
			var min = parseFloat($(this).attr("data-min"));
			var ref = $(this).attr("data-ref");
			var mask = new RegExp($(this).attr("data-mask"));
			
			var sValue = $("#"+element).val();
			var iValue = parseInt(sValue);			
			
			var validated = false;
			 
			switch(type){
				case "email":
					validated = /([a-z0-9\.\-_]+)(\@)(\w+)(\.)(([a-z0-9\-_]+)(\.))*([a-z0-9]+)/.test(sValue);					
					break;
				case "natural":
					validated = /[0-9]+/.test(sValue);
					break;
				case "integer":
					validated = /[+\-]?[0-9]+/.test(sValue);
					break;
				case "float":
					validated = /[\+\-]?[0-9]+(\.[0-9]+)?(E([\+\-]?[0-9]+))?/.test(sValue);
					break;
				case "mask":
					validated = mask.test(sValue);
					break;
				case "required":
					validated = (sValue != "" && sValue != null && sValue != undefined)? true : false;
					break;
				case "checked":
					value = $("#"+element+":checked").val();
					validated = (value != "" && value != null && value != undefined)? true : false;
					break;
				case "equals":
					validated = (sValue == ref);
					break;
				case "different":
					validated = (sValue != ref);
					break;
				case "range-value":
					validated = (iValue >= min && iValue <= max)? true: false;
					break;
				case "max-value":
					validated = (iValue <= max)? true: false;
					break;
				case "min-value":
					validated = (iValue >= min)? true: false;
					break;
				case "range-length":
					validated = (sValue.length >= min && sValue.length <= max)? true: false;
					break;
				case "max-length":
					validated = (sValue.length <= max)? true: false;
					break;
				case "min-length":
					validated = (sValue.length >= min)? true: false;
					break;
			}
			
			if (validated) {
				$(this).css(settings.cssOnValidated);
				settings.onValidated(this);
			} else {
				$(this).css(settings.cssOnFail);
				settings.onFail(this);
			}			
			success = success && validated;			
			
		});
		
		return success;
		
	};		
	
})(jQuery);