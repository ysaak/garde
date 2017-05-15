package ysaak.garde.gui.fiche.contract.create;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.converter.CurrencyStringConverter;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import ysaak.garde.business.utils.ContractUtils;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.data.contract.ContractType;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.data.parameter.Parameters;
import ysaak.garde.gui.common.GridFormHelper;
import ysaak.garde.gui.common.ParametersMap;
import ysaak.garde.gui.common.components.Card;
import ysaak.garde.gui.common.components.RadioButtonGroup;
import ysaak.garde.gui.common.components.fields.IntegerField;
import ysaak.garde.gui.common.components.fields.MonetaryField;
import ysaak.garde.gui.common.validation.StringValidators;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.service.translation.I18n;

import java.util.List;

public class ContractCreateView extends AbstractFormView<ContractDTO> {

  private VBox mainPane;

  // Child fields
  private TextField lastNameField;
  private TextField firstNameField;

  // Contract fields
  private RadioButtonGroup<ContractType> contractType;
  private IntegerField weeksPerYear;
  private IntegerField daysPerWeek;
  private IntegerField hoursPerWeek;

  private MonetaryField baseHourField;
  private MonetaryField increasedHourField;
  private Label monthlyPaymentValue;

  private final CurrencyStringConverter currencyStringConverter = new CurrencyStringConverter();

  private final ValidationSupport validationSupport = new ValidationSupport();

  private final ParametersMap parametersMap;

  public ContractCreateView() {
    super(I18n.get("contract.create.title"));
    parametersMap = new ParametersMap();
  }

  @Override
  public void initialize() {
    lastNameField = new TextField();
    validationSupport.registerValidator(lastNameField, false, StringValidators.mandatoryField());
    firstNameField = new TextField();
    validationSupport.registerValidator(firstNameField, false, StringValidators.mandatoryField());

    Card childCard = initChildSection();


    contractType = new RadioButtonGroup<>();
    contractType.setTextFactory(I18n::get);
    contractType.setItems(ContractType.FULL, ContractType.PARTIAL);

    weeksPerYear = new IntegerField();

    daysPerWeek = new IntegerField();
    validationSupport.registerValidator(daysPerWeek, false, StringValidators.rangedValueValidator(1, 7, Integer.class));
    hoursPerWeek = new IntegerField();


    baseHourField = new MonetaryField(0.);
    // FIXME fix monetary value validation
    //validationSupport.registerValidator(baseHourField, false, StringValidators.minValueValidator(0., Double.class));

    increasedHourField = new MonetaryField(0.);
    // FIXME fix monetary value validation
    //validationSupport.registerValidator(increasedHourField, false, StringValidators.minValueValidator(0., Double.class));

    monthlyPaymentValue = new Label(" ");

    Card contractCard = initContractSection();

    mainPane = new VBox(20);
    mainPane.setFillWidth(true);
    mainPane.getChildren().addAll(childCard.getView(), contractCard.getView());

    // Event handling
    contractType.selectedItemProperty().addListener((ob,ov,nv) -> contractTypeChanged(nv));
    contractTypeChanged(ContractType.FULL);

    weeksPerYear.valueProperty().addListener((ob, ov, nv) -> calculateMonthlyPayment(baseHourField.getValue(), hoursPerWeek.getValue(), nv));
    hoursPerWeek.valueProperty().addListener((ob, ov, nv) -> calculateMonthlyPayment(baseHourField.getValue(), nv, weeksPerYear.getValue()));
    baseHourField.valueProperty().addListener((ob, ov, nv) -> calculateMonthlyPayment(nv, hoursPerWeek.getValue(), weeksPerYear.getValue()));

    calculateMonthlyPayment(null, null, null);

    validationSupport.setValidationDecorator(new CompoundValidationDecoration(new StyleClassValidationDecoration("field-error", "field-warning"), new GraphicValidationDecoration()));
    validationSupport.initInitialDecoration();



    this.isValid.bind(Bindings.not(validationSupport.invalidProperty()));
  }

  private void contractTypeChanged(ContractType newValue) {
    if (newValue == ContractType.FULL) {
      weeksPerYear.setDisable(true);
      weeksPerYear.setText("52");

    }
    else {
      weeksPerYear.setDisable(false);
      weeksPerYear.setText("47");
    }
  }

  private void calculateMonthlyPayment(Number hourPrice, Integer hoursPerWeek, Integer attendanceWeek) {
    if (hourPrice != null && hoursPerWeek != null && attendanceWeek != null) {
      double monthlyPayment = ContractUtils.calculateMonthlyPayment(hourPrice.doubleValue(), hoursPerWeek, attendanceWeek);
      monthlyPaymentValue.setText(currencyStringConverter.toString(monthlyPayment));
    }
    else {
      monthlyPaymentValue.setText("--");
    }
  }

  private Card initChildSection() {
    GridFormHelper helper = new GridFormHelper(2);

    helper.addComponent(I18n.get("contract.create.child.lastName"), lastNameField, true);
    helper.addComponent(I18n.get("contract.create.child.firstName"), firstNameField, true);

    return new Card(I18n.get("contract.create.child.title"), helper.getPane());
  }

  private Card initContractSection() {
    final GridFormHelper helper = new GridFormHelper(3);

    helper.addSpanningComponent(I18n.get("contract.create.contract.type"), contractType.getView(), true, 3, 1);

    helper.addComponent(I18n.get("contract.create.contract.weeksPerYear"), weeksPerYear, true);
    helper.addComponent(I18n.get("contract.create.contract.daysPerWeek"), daysPerWeek, true);
    helper.addComponent(I18n.get("contract.create.contract.hoursPerWeek"), hoursPerWeek, true);

    helper.addComponent(I18n.get("parameters.contractSection.baseHourPrice"), baseHourField, true);
    helper.addComponent(I18n.get("parameters.contractSection.increasedHourPrice"), increasedHourField, true);
    helper.addComponent(I18n.get("contract.create.contract.monthlyPayment"), monthlyPaymentValue);

    return new Card(I18n.get("contract.create.contract.title"), helper.getPane());
  }

  @Override
  public Node getView() {
    return mainPane;
  }

  @Override
  public void setData(ContractDTO data) {

  }

  public void setParameters(List<ParameterDTO> parameters) {
    this.parametersMap.setParameters(parameters);

    // initialize field
    this.parametersMap.setField(Parameters.CONTRACT_BASE_HOUR_VALUE, baseHourField);
    this.parametersMap.setField(Parameters.CONTRACT_INCREASED_HOUR_VALUE, increasedHourField);
  }

  @Override
  public ContractDTO getData() {

    // Child data
    ChildDTO child = new ChildDTO();
    child.setLastName(lastNameField.getText());
    child.setFirstName(firstNameField.getText());

    ContractDTO contract = new ContractDTO();
    contract.setChild(child);
    contract.setType(contractType.getSelectedItem());
    contract.setWeekPerYear(weeksPerYear.getValue());
    contract.setAttendancePerWeek(daysPerWeek.getValue());
    contract.setHoursPerWeek(hoursPerWeek.getValue());
    contract.setBaseHourPrice(baseHourField.getValue());
    contract.setIncreasedHourValue(increasedHourField.getValue());

    return contract;
  }
}
