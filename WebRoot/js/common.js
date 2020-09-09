var rootpath = "../../Styles/skins/Aqua/icons/";

var common = {};
common.loading = false;

common.PrevImage = function (rootpath, paths) {
    for (var i in paths) {
        $('<img />').attr('src', rootpath + paths[i]);
    }
};

common.tip = function (info) {
    if (window.wintip) {
        window.wintip.set('content', info);
        window.wintip.show();
    }
    else {
        window.wintip = $.ligerDialog.tip({ title: '提示信息', content: info });
    }
    setTimeout(function () {
        window.wintip.hide()
    }, 3000);
};

common.loadForm = function (mainform, data, prefix) {
    prefix = prefix || "";
    if (data) {
        for (var p in data) {
            $("#" + prefix + p).val(data[p]);
        }
    }
};

common.submitForm = function (mainform, success) {
    if (!mainform)
        mainform = $("form:first");
    if (mainform.valid()) {
        mainform.ajaxSubmit({
            dataType: 'json',
            success: success,
            beforeSend: function () {
                $('body').append("<div class='ajaxloading'>正在保存数据中...</div>");
                $.ligerui.win.mask();
            },
            complete: function () {
                $('body > div.ajaxloading').remove();
                $.ligerui.win.unmask();
            }
        });
    }
    else {
        common.showInvalid();
    }
};

common.showInvalid = function (validator) {
    validator = validator || common.validator;
    if (!validator) return;
    var message = '<div class="invalid">存在' + validator.errorList.length + '个字段验证不通过，请检查!</div>';
    $.ligerDialog.error(message);
};

common.validate = function (form, options) {
    if (typeof (form) == "string")
        form = $(form);
    else if (typeof (form) == "object" && form.NodeType == 1)
        form = $(form);

    options = $.extend({
        errorPlacement: function (commonle, element) {
            if (!element.attr("id"))
                element.attr("id", new Date().getTime());
            if (element.hasClass("l-textarea")) {
                element.addClass("l-textarea-invalid");
            }
            else if (element.hasClass("l-text-field")) {
                element.parent().addClass("l-text-invalid");
            }
            $(element).removeAttr("title").ligerHideTip();
            $(element).attr("title", commonle.html()).ligerTip({
                distanceX: 5,
                distanceY: -3,
                auto: true
            });
        },
        success: function (commonle) {
            if (!commonle.attr("for")) return;
            var element = $("#" + commonle.attr("for"));

            if (element.hasClass("l-textarea")) {
                element.removeClass("l-textarea-invalid");
            }
            else if (element.hasClass("l-text-field")) {
                element.parent().removeClass("l-text-invalid");
            }
            $(element).removeAttr("title").ligerHideTip();
        }
    }, options || {});
    common.validator = form.validate(options);
    return common.validator;
};


common.appendSearchButtons = function (form, grid, filterbtn, callback) {
    if (!form) return;
    form = $(form);
    var container = $('<ul><li id="btn1container"></li><li id="btn2container"></li></ul><div class="l-clear"></div>').appendTo(form);
    if (!filterbtn) container.find("#btn2container").remove();
    common.addSearchButtons(form, grid, container.find("li:eq(0)"), filterbtn ? container.find("li:eq(1)") : null, callback);

};

common.bulidFilterGroup = function (form) {
    if (!form) return null;
    var group = { op: "and", rules: [] };
    $(":input", form).not(":submit, :reset, :image,:button, [disabled]")
        .each(function () {
            if (!this.name) return;
            if (!$(this).hasClass("field")) return;
            if ($(this).val() == null || $(this).val() == "") return;
            var op = $(this).attr("op") || "like";
            
            var vt = $(this).attr("vt") || "string";
            group.rules.push({
                op: op,
                field: this.name,
                value: $(this).val()
            });
        });
    return group;
};

common.createButton = function (options) {
    var p = $.extend({
        appendTo: $('body')
    }, options || {});
    var btn = $('<div class="button button2 buttonnoicon" style="width:60px"><div class="button-l"> </div><div class="button-r"> </div> <span></span></div>');
    if (p.icon) {
        btn.removeClass("buttonnoicon");
        btn.append('<div class="button-icon"> <img src="../' + p.icon + '" /> </div> ');
    }
    //绿色皮肤
    if (p.green) {
        btn.removeClass("button2");
    }
    if (p.width) {
        btn.width(p.width);
    }
    if (p.click) {
        btn.click(p.click);
    }
    if (p.text) {
        $("span", btn).html(p.text);
    }
    if (typeof (p.appendTo) == "string") p.appendTo = $(p.appendTo);
    btn.appendTo(p.appendTo);
};

common.addSearchButtons = function (form, grid, btn1Container, btn2Container, callback) {
    if (!form) return;
    if (btn1Container) {
        common.createButton({
            appendTo: btn1Container,
            text: '搜索',
            click: function () {
                var rule = common.bulidFilterGroup(form);
                loadClientData(rule);

                function loadClientData(data) {
                    var fnbody = ' return  ' + filterTranslator.translateGroup(data);
                    grid.loadData(new Function("o", fnbody));
                    if (callback) callback();
                }
            }
        });
    }
    if (btn2Container) {
        common.createButton({
            appendTo: btn2Container,
            text: '高级搜索>>',
            click: function () {
                grid.showFilter();
            }
        });
    }
};


common.showLoading = function (message) {
    message = message || "正在加载中...";
    $('body').append("<div class='ajaxloading'>" + message + "</div>");
    $.ligerui.win.mask();
};
common.hideLoading = function (message) {
    $('body > div.ajaxloading').remove();
    $.ligerui.win.unmask({ id: new Date().getTime() });
}

common.ajax = function (options) {
    var p = options || {};
    $.ajax({
        cache: false,
        url: p.url,
        data: p.data,
        dataType: 'json', type: 'post',
        beforeSend: function () {
            common.loading = true;
            common.showLoading(p.loading);
        },
        complete: function () {
            common.loading = false;
            common.hideLoading();
        },
        success: function (result) {
            if (!result.Error) {
                if (p.success)
                    p.success(result.Data, result.Message);
            }
            else {
                if (p.error)
                    p.error(result.Message);
            }
//        },
//        error: function (result) {
//            common.tip('执行失败' + result);
        }
    });
};


//以下为showFilter
$.ligerui.controls.Grid.prototype.showFilter = function () {
    var g = this, p = this.options;
    if (g.winfilter) {
        g.winfilter.show();
        return;
    }
    var filtercontainer = $('<div id="' + g.id + '_filtercontainer"></div>').width(380).height(120).hide();
    var filter = filtercontainer.ligerFilter({ fields: getFields() });
    return g.winfilter = $.ligerDialog.open({
        width: 420, height: 208,
        target: filtercontainer, isResize: true, top: 50,
        buttons: [
            { text: '确定', onclick: function (item, dialog) { loadData(); dialog.hide(); } },
            { text: '取消', onclick: function (item, dialog) { dialog.hide(); } }
            ]
    });

    //将grid的columns转换为filter的fields
    function getFields() {
        var fields = [];
        //如果是多表头，那么g.columns为最低级的列
        $(g.columns).each(function () {
            var o = { name: this.name, display: this.display };
            var isNumber = this.type == "int" || this.type == "number" || this.type == "float";
            var isDate = this.type == "date";
            if (isNumber) o.type = "number";
            if (isDate) o.type = "date";
            if (this.editor) {
                o.editor = this.editor;
            }
            fields.push(o);
        });
        return fields;
    }

    function loadData() {
        var data = filter.getData();
        if (g.dataAction == "server") {
            //服务器过滤数据
            loadServerData(data);
        }
        else {
            //本地过滤数据
            loadClientData(data);
        }
    }

    function loadServerData(data) {
        if (data && data.rules && data.rules.length) {
            g.set('parms', { where: JSON2.stringify(data) });
        } else {
            g.set('parms', {});
        }
        g.loadData();
    }
    function loadClientData(data) {
        var fnbody = ' return  ' + filterTranslator.translateGroup(data);
        g.loadData(new Function("o", fnbody));
    }

};

//以下为filterTranslator
var filterTranslator = {

    translateGroup: function (group) {
        var out = [];
        if (group == null) return " 1==1 ";
        var appended = false;
        out.push('(');
        if (group.rules != null) {
            for (var i in group.rules) {
                var rule = group.rules[i];
                if (appended)
                    out.push(this.getOperatorQueryText(group.op));
                out.push(this.translateRule(rule));
                appended = true;
            }
        }
        if (group.groups != null) {
            for (var j in group.groups) {
                var subgroup = group.groups[j];
                if (appended)
                    out.push(this.getOperatorQueryText(group.op));
                out.push(this.translateGroup(subgroup));
                appended = true;
            }
        }
        out.push(')');
        if (appended == false) return " 1==1 ";
        return out.join('');
    },

    translateRule: function (rule) {
        var out = [];
        if (rule == null) return " 1==1 ";
        if (rule.op == "like" || rule.op == "startwith" || rule.op == "endwith") {
            out.push('/');
            if (rule.op == "startwith")
                out.push('^');
            out.push(rule.value);
            if (rule.op == "endwith")
                out.push('$');
            out.push('/i.test(');
            out.push('o["');
            out.push(rule.field);
            out.push('"]');
            out.push(')');
            return out.join('');
        }
        out.push('o["');
        out.push(rule.field);
        out.push('"]');
        out.push(this.getOperatorQueryText(rule.op));
        out.push('"');
        out.push(rule.value);
        out.push('"');
        return out.join('');
    },


    getOperatorQueryText: function (op) {
        switch (op) {
            case "equal":
                return " == ";
            case "notequal":
                return " != ";
            case "greater":
                return " > ";
            case "greaterorequal":
                return " >= ";
            case "less":
                return " < ";
            case "lessorequal":
                return " <= ";
            case "and":
                return " && ";
            case "or":
                return " || ";
            default:
                return " == ";
        }
    }

};