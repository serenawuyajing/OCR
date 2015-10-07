'use strict';

var THESHOLD = 0.9;
var REQUEST = {
  content: '“Curiosity now has the chance, for example, to do some closer up, but still remote, measurements, using the ChemCam instrument with lasers, to look at composition. I understand there is increasing pressure from the science side to allow that, given this new discovery.”\n\nAn organisation called the committee on space research (Cospar) draws up the rules on what is called planetary protection, which exist to prevent missions from Earth contaminating the pristine environments of other worlds. Landers that are searching for life must be exceptionally clean, and fall under category IVb, but those entering special regions are category IVc missions and must be cleaner still.\n\nCuriosity was designed for category IVb, and under Cospar rules is not allowed to enter areas where water might be flowing. But that might be up for discussion. Nasa’s Jim Green argues that the intense radiation environment on Mars, in particular the ultraviolet light, might have killed any bugs Curiosity carried into space, and so may be clean enough to move into the sites.\n\nA recent report from the US National Academy of Sciences and the European Science Foundation, however, suggests that UV light might not do the job, and could make matters worse. “Although the flux of ultraviolet radiation within the Martian atmosphere would be deleterious to most airborne microbes and spores, dust could attenuate this radiation and enhance microbial viability,” the report states.\n\nCuriosity could inspect the flows from a distance, using its onboard laser to take more measurements of the dark streaks. But a more controversial option is to find a flat region at the bottom of one of the flows, and scoop up some Martian soil for analysis.\n\nThe next rover due to land on the planet is a joint mission named ExoMars from the European and Russian space agencies, set to launch in 2018. The plan is for the rover to drill up to two metres into the Martian soil to look for life past or present.\n\n“For the ExoMars 2018 rover, the planetary protection is being very carefully looked at and a combination of baking and cleaning is planned to avoid any possible mishaps and make sure it is IVb so it can make the best possible life-searching measurements in the regions it can get to,” said Coates, who is leading the camera team on the rover.',
  components: [true,true,true,true,true,true]
};
var RESPONSE = {
  errors: [{
    name: 'for',
    position: 31,
    candidates: [{
      name: 'as',
      confidence: 0.90
    },{
      name: 'be',
      confidence: 0.28
    }]
  },{
    name: 'organisation',
    position: 274,
    candidates: [{
      name: 'organization',
      confidence: 0.92
    },{
      name: 'organ',
      confidence: 0.28
    }]
  },{
    name: 'camera',
    position: 2287,
    candidates: [{
      name: 'camero',
      confidence: 0.36
    },{
      name: 'came',
      confidence: 0.11
    }]
  }]
};

(function() {
  var app = angular.module('mibioApp', ['file-model']);

  app.directive('onReadFile', function($parse) {
    return {
      restrict: 'A',
      scope: false,
      link: function(scope, element, attrs) {
        var fn = $parse(attrs.onReadFile);
        element.on('change', function(onChangeEvent) {
          var reader = new FileReader();
          reader.onload = function(onLoadEvent) {
            scope.$apply(function() {
              fn(scope, {$fileContent:onLoadEvent.target.result});
            });
          };
          reader.readAsText((onChangeEvent.srcElement || onChangeEvent.target).files[0]);
        });
      }
    };
  });

  app.directive('ngRightClick', function($parse){
    return function(scope, element, attrs){
      var fn = $parse(attrs.ngRightClick);
      element.bind('contextmenu', function(event){
        scope.$apply(function(){
          event.preventDefault();
          fn(scope, {$event:event});
        });
      });
    };
  });

  app.controller('AppStateController', function(){
    this.states = {
      READY:     1,
      WAIT:      2,
      PROCESSED: 3
    };
    this.state = this.states.READY;
    this.submit = function() {
      this.state = this.states.WAIT;
    };
    this.responsed = function() {
      var errors = RESPONSE.errors;
      for (var i = 0; i < errors.length; i++) {
        var cands = errors[i].candidates;
        for (var j = 0; j < cands.length; j++) {
          if (cands[j].confidence >= THESHOLD) {
            errors[i].state = 'corrected';
            errors[i].display = cands[j].name;
            break;
          }
        }
        if (!errors[i].state) {
          errors[i].state = 'incorrect';
          errors[i].display = errors[i].name;
        }
      }
      this.state = this.states.PROCESSED;
    };
    this.done = function() {
      this.state = this.states.READY;
    };
  });

  app.controller('UserInputController', function($scope){
    this.content = null;
    this.show = false;
    this.label = 'Advanced options';
    this.components = [
      {name:'Wikipedia Dictionary'     , selected:true},
      {name:'Domain Specfic Dictionary', selected:true},
      {name:'Lexical Dictionary'       , selected:true},
      {name:'Another Dictionary'       , selected:true},
      {name:'Google Unigram'           , selected:true},
      {name:'Google Five-gram'         , selected:true}
    ];
    this.read = function($fileContent){
      this.content = $fileContent;
      console.log($fileContent);
    }
    this.togglePanel = function(){
      this.show = (this.show === true ? false : true);
      this.label = (this.show === true ? 'Correction components' : 'Advanced options');
    };
  });

  app.controller('DisplayController', function() {
    this.content = REQUEST.content;
    this.errors = RESPONSE.errors;
    this.toHtml = function($scope, $sce) {
      var lines = this.content.split('\n');
      var html = '<p>' + lines.join('</p><p>') + '</p>';
      return $sce.trustAsHtml(html);
    };
    this.curr = -1;
    this.checkAll = function() {
      for (var i = 0; i < this.errors.length; i++) {
        this.errors[i].state = 'checked';
      }
    };
    this.numErrors = function() {
      return this.errors.length;
    };
    this.numChecked = function() {
      var num = 0;
      for (var i = 0; i < this.errors.length; i++) {
        if (this.errors[i].state === 'checked') {
          num++;
        }
      }
      return num;
    };
    this.download = function() {
      if (this.numChecked() != this.numErrors()) {
        alert("You have to check all the error first.");
        return;
      }
      var output = this.content;
      var pChange = 0;
      for (var i = 0; i < this.errors.length; i++) {
        var pos = this.errors[i].position + pChange;
        output = output.substring(0, pos) + this.errors[i].display + output.substring(pos + this.errors[i].name.length, output.length);
        pChange += this.errors[i].display.length - this.errors[i].name.length;
      }
      saveAs(new Blob([output], {type: 'text/plain;charset=utf-8'}), 'output.txt');
    };
    this.display = function(idx) {
      if (this.curr === idx) {
        this.curr = -1;
      } else {
        this.curr = idx;
      }
    };
    this.select = function(errIdx, candIdx) {
      if (candIdx === undefined) {
        this.errors[errIdx].display = this.errors[errIdx].name;
      } else {
        this.errors[errIdx].display = this.errors[errIdx].candidates[candIdx].name;
      }
      this.errors[errIdx].state = 'checked';
    };
    this.check = function(idx) {
      if (this.errors[idx].state === 'checked') {
        var cands = this.errors[idx].candidates;
        if (this.errors[idx].display === this.errors[idx].name) {
          this.errors[idx].state = 'incorrect';
        } else {
          for (var j = 0; j < cands.length; j++) {
            if (cands[j].name === this.errors[idx].display) {
              if (cands[j].confidence >= THESHOLD) {
                this.errors[idx].state = 'corrected';
              } else {
                this.errors[idx].state = 'incorrect';
              }
              break;
            }
          }
        }
      } else {
        this.errors[idx].state = 'checked';
      }
    };
    this.beforeError = function(idx) {
      var str = 0;
      var end = this.errors[idx].position;
      if (idx > 0) {
        str = this.errors[idx - 1].position + this.errors[idx - 1].name.length;
      }
      return this.content.substring(str, end);
    };
    this.afterError = function(idx) {
      if (idx < this.errors.length - 1) {
        return "";
      }
      var str = this.errors[idx].position + this.errors[idx].name.length;
      var end = this.content.length;
      if (idx < this.errors.length - 1) {
        end = this.errors[idx + 1].position;
      }
      return this.content.substring(str, end);
    };
  });
})();
